package org.opencds.fhir.rest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SepsisRestService {
	private static final Log log = LogFactory.getLog(SepsisRestService.class);
	private java.nio.file.Path responseConfigFile;

	public SepsisRestService(String responseConfigFile) {
		this.responseConfigFile = Paths.get(responseConfigFile);
	}

	@POST
	@Path("/sepsis/cda")
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.TEXT_XML)
	public Response sepsis(@PathParam("cda") String cda) throws IOException {
		log.error(cda);
		InputStream responseData = new FileInputStream(getProperties().getProperty("response"));
		StringWriter writer = new StringWriter();
		BufferedReader reader = new BufferedReader(new InputStreamReader(responseData));
		String line;
		while ((line = reader.readLine()) != null) {
			writer.write(line);
		}
		return Response.ok().entity(writer.toString()).build();
	}

	private Properties getProperties() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(responseConfigFile.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return props;
	}

}
