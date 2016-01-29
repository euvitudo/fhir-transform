import org.apache.cxf.jaxrs.impl.ResponseImpl
import org.opencds.fhir.rest.SepsisRestService

import spock.lang.Specification

class SepsisRestServiceSpec extends Specification {
	
	def "test sepsis"() {
		when:
		ResponseImpl data = new SepsisRestService().sepsis('');
		
		then:
		data.getEntity() == new File('src/main/resources/dummy-response.xml').text.replaceAll('\\n', "")
		true
	}

}
