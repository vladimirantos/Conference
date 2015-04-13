package conference.model;

import conference.exceptions.ApplicationException;
import conference.exceptions.FileNotUploadedException;
import conference.model.entity.Article;
import conference.model.entity.Author;
import conference.model.entity.DbArticle;
import conference.model.repository.ArticleRepository;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleManager{

    private Article article;

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleManager setArticle(Article article){
        this.article = article;
        return this;
    }

    public void saveConfigFile() {
        MultipartFile multipartFile = article.getConfigFile();
        try {
            uploadConfigFile(multipartFile)
                    .insertToDatabase(multipartFile.getOriginalFilename());
        } catch (IOException e) {
            throw new FileNotUploadedException("Nepodařilo se nahrát soubor");
        }
    }

    private ArticleManager uploadConfigFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!"".equalsIgnoreCase(fileName)){
            File f = new File(FileStorage.getPath(article.getConference()) + fileName);
            file.transferTo(f);
        }

        return this;
    }

    private void insertToDatabase(String fileName){
        ArticleXmlParser articleXmlParser = new ArticleXmlParser(FileStorage.getPath(article.getConference()) + fileName, article.getConference());
        try {
            List<DbArticle> dbArticles = articleXmlParser.parse();
            articleRepository.insertAll(dbArticles);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            throw new ApplicationException("Soubor má nepovolený formát.");
        }
    }
}

/**
 * Třída zpracovává konfigurační soubor a ukládá články do databáze
 */
class ArticleXmlParser{
    private File xml;

    private long conference;

    public ArticleXmlParser(String path, long conference){
        xml = new File(path);
        this.conference = conference;
    }

    public List<DbArticle> parse() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);
        doc.getDocumentElement().normalize();
        NodeList articles = doc.getElementsByTagName("article");
        List<DbArticle> dbArticles = new ArrayList<DbArticle>();
        for(int i = 0; i < articles.getLength(); i++){
            Node article = articles.item(i);
            if (article.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) article;
                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String abstrct = element.getElementsByTagName("abstract").item(0).getTextContent();
                List<Author> authors = parseAuthors(element.getElementsByTagName("author"));
                DbArticle dbArticle = new DbArticle();
                dbArticle.setName(title);
                dbArticle.setAbstrct(abstrct);
                dbArticle.setAuthors(authors);
                dbArticle.setConference(conference);
                dbArticles.add(dbArticle);
            }
        }
        return dbArticles;
    }

    private List<Author> parseAuthors(NodeList nodes){
        List<Author> authors = new ArrayList<Author>();
        Log.message("POCET AUTHOR", String.valueOf(nodes.getLength()), this);
        for(int i = 0; i < nodes.getLength(); i++){
            Author author = new Author();
            Node n = nodes.item(i);
                String name = n.getTextContent();
                String[] parseName = name.split(" ");
                author.setName(parseName[0]);
                author.setLastName(parseName[1]);
            authors.add(author);
        }
        return authors;
    }
}
