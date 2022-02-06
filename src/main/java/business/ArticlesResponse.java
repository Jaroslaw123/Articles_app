package business;
import java.util.List;

public class ArticlesResponse {

    private String status;
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }
}