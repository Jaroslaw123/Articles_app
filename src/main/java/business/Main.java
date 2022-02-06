package business;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        // create a Httpclient
        HttpClient client = HttpClient.newHttpClient();
        // pageSize - The number of results to return per page (request). 20 is the default, 100 is the maximum.
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://newsapi.org/v2/top-headlines?" +
                "country=pl&category=business&from=2022-01-20" +
                "&sortBy=publishedAt&apiKey=b88b004c5e95444eb91b8029501b3f71")).build();

        ArticlesResponse articlesResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(ArticleResponseParser::parse)
                .join();

        String path = "articles.csv";
        final String CSV_SEPARATOR = ":";
        String charsetName = "UTF-8";
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), charsetName))) {
            for (Article article : articlesResponse.getArticles()) {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(article.getTitle());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(article.getDescription());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(article.getAuthor());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
}
