package controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Alarma;
import model.Cliente;
import model.DtoClienteAlarmas;
import model.DtoInformeAlarmas;
import model.Sensor;
import service.ServiceInformes;
import service.ServiceClientes;

@Controller
public class InformesController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
/*	@Autowired
	@Qualifier("fechaValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}*/

	@Autowired
	ServiceClientes sClientes;
	@Autowired
	ServiceInformes sInformes;

	private static List<Cliente> clientes; //= new ArrayList<>();
	@ModelAttribute("clientes")
	public List<Cliente> iniializarClientes() {
		if(clientes == null) {
			clientes = sClientes.getClientes();
		System.out.println("Tamaño del listado de clientes: "+clientes.size());
		}
		return clientes;
	}
	
	@RequestMapping(value="/adminInformeCliente.to",method = {RequestMethod.GET,RequestMethod.POST})
	public String recuperarClientes(/*HttpSession sesion, @RequestParam(required = false, name = "actualizar") boolean update*/HttpServletRequest request, Model vista) {
		if(request.getParameter("actualizar") != null) {
			vista.addAttribute("clientes",sClientes.getClientes());
		System.out.println("Tamaño del listado de clientes: "+clientes.size());
		}
		return "admin-informe_cliente";
	}
	
	@RequestMapping(value="/adminInformeFullCliente.to",method = {RequestMethod.GET,RequestMethod.POST})
	public String recuperarFullClientes(HttpServletRequest request) {
		request.setAttribute("clientes",sClientes.getDtoClientes());
		return "admin-informeFullCliente";
	}
	
	@PostMapping(value = "/adminInformeFullCliente.do")
	public String informeFullCliente(@RequestParam("dni") String dni, HttpServletRequest request) {
		List<Sensor> sensores = sInformes.getInformeFullActividad(dni);
		request.setAttribute("sensores", sensores);
		// request.setAttribute("valorSeleccionado", dni);
		return "forward:/adminInformeFullCliente.to";
	}
	
	@GetMapping("informes")
	public String index(@RequestParam("dni") String dni, HttpServletRequest request, ModelMap modelMap) {
		List<Sensor> sensores = sInformes.getInformeFullActividad(dni);
		PagedListHolder pagedListHolder = new PagedListHolder(sensores);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(3);
		modelMap.put("pagedListHolder", pagedListHolder);
		return "informes/cliente"; //
	}

	@RequestMapping(value="/adminInformeTemporal/{pageNumber}",method = {RequestMethod.GET,RequestMethod.POST},params = {"fecIni", "fecFin"})    
    public String informePaginado(@PathVariable int pageNumber, @RequestParam(required = false, name = "n_filas") Integer pageSize, @RequestParam("fecIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecIniJ, @RequestParam("fecFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecFinJ, Model request){    
        if(pageSize == null) pageSize=5;   //total 
        int countResult = sInformes.recuentoInformeTemporal(fecIniJ, fecFinJ);
        int lastPageNumber = countResult % pageSize == 0 ? countResult / pageSize : (countResult / pageSize) + 1;
    System.out.println("Total de páginas: "+lastPageNumber);  
		if(fecFinJ == null) fecFinJ = new Date(); //Si la fecha de Fin si está vacia se asume que es la actual.
	System.out.println("*********************/adminInformeTemporal***********************************");
		if(fecIniJ != null) System.out.println("fecIniJ = " + fecIniJ.toString());
	System.out.println("fecFinJ = " + fecFinJ.toString());
        List<DtoInformeAlarmas> informeB = sInformes.getInformeTemporalByPage(fecIniJ, fecFinJ, pageNumber, pageSize);    
        request.addAttribute("informe", informeB);  
        request.addAttribute("n_paginas", lastPageNumber);  
  		request.addAttribute("tipo_informe", "temporal");
        return "admin-informeTemporal";    
    }    	
//	@Validated
	@RequestMapping(value = "/adminInformeTemporal.do",method = {RequestMethod.GET,RequestMethod.POST},params = {"fecIni", "fecFin"})
	public String informeTemporal(@RequestParam(required = false, name = "agrupar") boolean infCliente, @RequestParam("fecIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecIniJ, @RequestParam("fecFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecFinJ,
			/*BindingResult bindingResult,*/ Model request) {
/*		validator.validate(fecFinJ, bindingResult);
		if (bindingResult.hasErrors()) {
			LOG.info("Returning admin-informeTemporal.jsp page");
			return "admin-informeTemporal"; // @NotBlank @Size(max = 10) @Valid @Pattern(regexp = "^\d{4}\-\d{2}\-\d{2}$")
		}*/
		if(fecFinJ == null) fecFinJ = new Date(); //Si la fecha de Fin si está vacia se asume que es la actual.
//		if(fecIniJ == null || fecIniJ.after(fecFinJ)) return "admin-informeTemporal";
		System.out.println("*********************/adminInformeTemporal.do***********************************");
		if(fecIniJ != null) System.out.println("fecIniJ = " + fecIniJ.toString());
		System.out.println("fecFinJ = " + fecFinJ.toString());
		if(infCliente) {
			List<DtoClienteAlarmas> informeC = sInformes.getInformeTemporalClientes(fecIniJ, fecFinJ);
//			for(DtoClienteAlarmas inf:informeC) System.out.println("Cliente: "+inf.getDni());
			request.addAttribute("informe", informeC);
			request.addAttribute("tipo_informe", "agregodo_cliente");
			return "admin-informe_temporal";
		} else {
		// List<Object[]> informe = sInformes.getInformeFullTemporal(fecIniO, fecFinO);
		// System.out.println("Listado (filas): "+informe.size());
		// for(int i=0; i<informe.get(0).length; i++)
		// System.out.println(informe.get(0)[i].toString());
		List<DtoInformeAlarmas> informeA = sInformes.getInformeTemporal(fecIniJ, fecFinJ);
		// for(DtoInformeAlarmas inf:informeA) System.out.println("Cliente:
		// "+inf.getDni());
		request.addAttribute("informe", informeA);
		request.addAttribute("tipo_informe", "temporal");
//		request.setAttribute("valorFecIni", fecIniJ);
//		request.setAttribute("valorFecFin", fecFinJ);
		}
		return "admin-informeTemporal";
	}

	@RequestMapping(value = "/adminInformeTemporalClientes.do",method = {RequestMethod.GET,RequestMethod.POST})
	public String informeTemporalClientes(@RequestParam("fecIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecIniJ,
			@RequestParam("fecFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecFinJ,
			Model request){
		System.out.println("*********************/adminInformeTemporalClientes.do***********************************");
		System.out.println("fecIniJ = "+fecIniJ.toString());
		System.out.println("fecFinJ = "+fecFinJ.toString());
//		String fecIniO = fecIniJ.replaceAll("-", "");
//		String fecFinO = fecFinJ.replaceAll("-", "");
//		System.out.println("fecIniO = "+fecIniO);
//		System.out.println("fecFinO = "+fecFinO);
		
			List<DtoClienteAlarmas> informeC = sInformes.getInformeTemporalClientes(fecIniJ, fecFinJ);
			for(DtoClienteAlarmas inf:informeC) System.out.println("Cliente: "+inf.getDni());
			request.addAttribute("informe", informeC);
//			request.setAttribute("valorFecIni", fecIniJ);
//			request.setAttribute("valorFecFin", fecFinJ);
			return "admin-informe_temporal";
	}

	@ExceptionHandler(Exception.class)
	public String exception(HttpServletRequest request, Model model, Exception e) {
	    LOG.error("Request: " + request.getRequestURL() + " raised " + e);
	    model.addAttribute("url", request.getRequestURI());
		model.addAttribute("exception", e.getMessage());
		return "error";
	}

}