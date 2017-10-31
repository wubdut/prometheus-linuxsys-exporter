package com.LinuxSysExporter;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.LinuxSysExporter.basic.Configuration;
import com.LinuxSysExporter.service.TopService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Controller
@SpringBootApplication
public class LinuxSysExporter {
	
    private List<Gauge> systemGaugeList;
    
    private TopService topService;
    
    public LinuxSysExporter() throws IOException {
    	Configuration conf = new Configuration();
    	topService = new TopService(conf.ip, conf.userName, conf.userPwd); // Read config to get the target.
    }
    
    @RequestMapping(path = "/metrics")
    public void metrics(Writer responseWriter) throws IOException {
    	topService.register("topApp", "iotopApp", "iftopApp").run();
        TextFormat.write004(responseWriter, CollectorRegistry.defaultRegistry.metricFamilySamples());
        responseWriter.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(LinuxSysExporter.class, args);
    }
    
}
