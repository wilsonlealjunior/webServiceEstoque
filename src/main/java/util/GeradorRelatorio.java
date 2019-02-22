package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationContextLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.webServiceEstoque.dao.VendaDAO;
import org.webServiceEstoque.models.Venda;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;


public class GeradorRelatorio {



	public void RelatorioVendas(List<Venda> vendas) throws JRException, FileNotFoundException{
		//JasperCompileManager.compileReportToFile("/WEB-INF/jasper/vendas.jasper"); 

        Map<String, Object> parametros = new HashMap<String, Object>();
             
        JRDataSource dataSource = new JRBeanCollectionDataSource(vendas,false);
        JasperPrint print = JasperFillManager.fillReport("vendas.jasper", parametros, dataSource);


        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("vendas.pdf"));
        exporter.exportReport();


	}

}
