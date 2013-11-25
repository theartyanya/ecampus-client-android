package ua.kpi.campus.jsonparsers;

/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 24 лист. 2013
 */
public class JSONParserTest {
	
	public static void main(String[] args) {		
		
		
		Authorization auth = JSONAuthorizationParser.parse("{			  \"StatusCode\": 200,"
								+ " \"TimeStamp\": \"2013-11-23T01:59:34.2301641-08:00\","
								+ "\"Guid\": \"aafa9bcd-ddbc-40bc-85f6-003453ed5eec\","
								+ "\"Paging\": null,"
								+ "\"Data\": \"kolya-test-session-id\""
								+ "}) );");
		GetPermissions gpsd=JSONGetPermissionsParser.parse("{"
			 +" \"StatusCode\": 20+0,"
			 +" \"TimeStamp\": \"2013-11-23T01:59:39.9865742-08:00\","
			 +"\"Guid\": \"3d6da232-89b3-4dbf-8718-b901d04bf61f\","
			 +"\"Paging\": null,"
			 +"\"Data\": ["
			 +" {"
			   +"   \"SubsystemName\": \"Кафедра прикладной математики\","
			   +" \"IsCreate\": false,"
			   +" \"IsRead\": true,"
			   +" \"IsUpdate\": false,"
			   +" \"IsDelete\": false"
			   +"},"
			   +" {"
			    +"  \"SubsystemName\": \"Кафедра иностранных языков\","
			    +"   \"IsCreate\": true,"
			    +"   \"IsRead\": true,"
			    +"   \"IsUpdate\": true,"
			    +"   \"IsDelete\": false"
			    +"  }"
			 +" ]"
			 +"}");
		System.out.println( auth.toString());
		System.out.println( " ==========================================================\n");
		System.out.println( gpsd.getGuid());

	}
}
