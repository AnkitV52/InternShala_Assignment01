import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import org.json.simple.*;

public class JavaMain {

	public static void main(String[] args) {

		String token = GetToken();

	  try {

		if(token != "" && token != null)
		{

			System.out.println("Fetching Shipping label.... \n");

		URL url = new URL("https://sandbox-api.flipkart.net/sellers/v3/shipments/FMPP0884852585/labels");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        String basicAuth = "Bearer " + token;

        conn.setRequestProperty("Authorization", basicAuth);

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
	}else{
		System.out.println("Token not found...");
	}

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

	}


	public static String GetToken(){
       try {

		System.out.println("Fetching the token \n");
		URL url = new URL("https://sandbox-api.flipkart.net/oauth-service/oauth/token?grant_type=client_credentials&scope=Seller_Api");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		byte[] message = ("151888b133a2692b399752442789528213223:fdf512d925a3d2a41cac91ade9bbaa6e").getBytes("UTF-8");
		String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(message);
        conn.setRequestProperty("Authorization", "Basic "+encoded);

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			Object obj=JSONValue.parse(output);  
    		JSONObject jsonObject = (JSONObject) obj; 
			String token = (String) jsonObject.get("access_token");  
			System.out.println("Token we get...: "+token + "\n");
			return token;
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

return null;


	}

}