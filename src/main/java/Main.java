import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost1 = new HttpPost("https://en.wikipedia.org/w/index.php?search=hores");
            response = httpClient.execute(httpPost1);

            if (200 == response.getStatusLine().getStatusCode()) {
                if (EntityUtils.toString(response.getEntity()).contains("title=\"Hores (page does not exist)\""))
                    System.out.println("Страница \"Hores\" не существует!");
                else System.out.println("Страница \"Hores\" существует!");
            }
            else System.out.println("Unacceptable responce status!");

            HttpPost httpPost2 = new HttpPost("https://en.wikipedia.org/w/index.php?search=horse");
            response = httpClient.execute(httpPost2);

            if (302 == response.getStatusLine().getStatusCode())
                System.out.println("перекидывает на статью про лошадь" +
                        response.getHeaders("Location")[0].toString());
            else System.out.println("не перекидывает на статью про лошадь");

        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
