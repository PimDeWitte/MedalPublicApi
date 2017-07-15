package tests.com.gomedal.medal;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pimdewitte on 7/11/17.
 */
public class JavaBotExample {


    /**
     * Stores the categories (Also known as games)
     */
    static ExampleCategoryObject[] categories = null;

    static HashMap<Integer, ExampleCategoryObject> categoriesById = new HashMap<>();
    static HashMap<String, ExampleCategoryObject> categoriesByName = new HashMap<>();


    public static void main(String[] args) {
       getCategories();
       getRandomClip();
       getGameSpecificTopClips(10, "pubg");
       getTopClips(10);
    }

    /**
     * Gets the categories and adds them to the categories by id hashmap
     */

    public static void getCategories() {
        HttpResponse<String> stringResponse = null;
        HttpResponse<JsonNode> jsonResponse = null;


        try {
            stringResponse = Unirest.get("https://api.gomedal.com/categories")
                    .header("accept", "application/json")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();

        categories = g.fromJson(stringResponse.getBody(), ExampleCategoryObject[].class);

        for (int i = 0; i < categories.length; i++) {
            categoriesById.put(categories[i].getCategoryId(), categories[i]);
            categoriesByName.put(categories[i].getCategoryName().toLowerCase(), categories[i]);
            System.out.println("Category id: " + categories[i].getCategoryId() + ", Category name: " + categories[i].getCategoryName());
        }
    }

    /**
     * Prints a random clip
     */

    public static void getRandomClip() {
        HttpResponse<String> stringResponse = null;
        HttpResponse<JsonNode> jsonResponse = null;

        ExampleContentObject[] c = null;
        try {
            stringResponse = Unirest.get("https://api.gomedal.com/botclips?random=1")
                    .header("accept", "application/json")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();

        c = g.fromJson(stringResponse.getBody(), ExampleContentObject[].class);

        if(c.length < 1) {
            System.out.println("No random clip was returned");
            return;
        }
        System.out.println(
                "\ncontentTitle=" +
                c[0].getContentTitle() +
                "\n,contentUrl="+
                c[0].getContentUrl() +
                "\n,contentCredits="+
                c[0].getContentCredits()+
                "\n,contentThumbnail1080p="+
                c[0].getThumbnail720p() +
                "\n,videoLengthSeconds="+
                c[0].getVideoLengthSeconds()+
                "\n,categoryId="+
                c[0].getCategoryId()+
                "\n,category="+
                categoriesById.get(c[0].getCategoryId()).getCategoryName()); // SEE HERE FOR EXAMPLE USE OF GETTING A CATEGORY BY ITS ID
    }


    /**
     * Gets game clips for a specific game
     * @param amount
     * @param categoryName
     */

    public static void getGameSpecificTopClips(int amount, String categoryName) {
        HttpResponse<String> stringResponse = null;
        HttpResponse<JsonNode> jsonResponse = null;

        ExampleContentObject[] c = null;
        int categoryId = -1;

        if(categoriesByName.containsKey(categoryName.toLowerCase())) {
            categoryId = categoriesByName.get(categoryName.toLowerCase()).getCategoryId();
        } else {
            System.out.println("No proper categoryName specified");
            return;
        }
        try {
            stringResponse = Unirest.get("https://api.gomedal.com/botclips?limit="+amount+"&categoryId="+categoryId)
                    .header("accept", "application/json")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        System.out.println(stringResponse.getBody());

        c = g.fromJson(stringResponse.getBody(), ExampleContentObject[].class);

        if(c.length < 1) {
            System.out.println("No clip was returned");
            return;
        }

        for(int i = 0; i < c.length; i++) {
            System.out.println(
                    "\ncontentTitle=" +
                            c[i].getContentTitle() +
                            "\n,contentUrl=" +
                            c[i].getContentUrl() +
                            "\n,contentCredits=" +
                            c[i].getContentCredits() +
                            "\n,contentThumbnail1080p=" +
                            c[i].getThumbnail720p() +
                            "\n,videoLengthSeconds=" +
                            c[i].getVideoLengthSeconds() +
                            "\n,categoryId=" +
                            c[i].getCategoryId() +
                            "\n,category=" +
                            categoriesById.get(c[i].getCategoryId()).getCategoryName()); // SEE HERE FOR EXAMPLE USE OF GETTING A CATEGORY BY ITS ID
        }
    }

    /**
     * Gets top game clips for all games combined
     * @param amount
     */

    public static void getTopClips(int amount) {
        HttpResponse<String> stringResponse = null;
        HttpResponse<JsonNode> jsonResponse = null;

        ExampleContentObject[] c = null;
        int categoryId = -1;

        try {
            stringResponse = Unirest.get("https://api.gomedal.com/botclips?limit="+amount)
                    .header("accept", "application/json")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        System.out.println(stringResponse.getBody());

        c = g.fromJson(stringResponse.getBody(), ExampleContentObject[].class);

        if(c.length < 1) {
            System.out.println("No clip was returned");
            return;
        }

        for(int i = 0; i < c.length; i++) {
            System.out.println(
                    "\ncontentTitle=" +
                            c[i].getContentTitle() +
                            "\n,contentUrl=" +
                            c[i].getContentUrl() +
                            "\n,contentCredits=" +
                            c[i].getContentCredits() +
                            "\n,contentThumbnail1080p=" +
                            c[i].getThumbnail720p() +
                            "\n,videoLengthSeconds=" +
                            c[i].getVideoLengthSeconds() +
                            "\n,categoryId=" +
                            c[i].getCategoryId() +
                            "\n,category=" +
                            categoriesById.get(c[i].getCategoryId()).getCategoryName()); // SEE HERE FOR EXAMPLE USE OF GETTING A CATEGORY BY ITS ID
        }
    }
}

class ExampleCategoryObject {
    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryThumbnail() {
        return categoryThumbnail;
    }

    public int getCategoryFollowers() {
        return categoryFollowers;
    }

    public int getCategoryPublishers() {
        return categoryPublishers;
    }

    public int getIsFeatured() {
        return 0;
    }

    private final int categoryId;
    private final String categoryName;
    private final String categoryThumbnail;
    private final String categoryBackground;
    private final int categoryFollowers;
    private final int categoryPublishers;
    //int isFeatured = 0; 

    public ExampleCategoryObject(int categoryId, String categoryName, String categoryThumbnail, int categoryFollowers, int categoryPublishers) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryThumbnail = categoryThumbnail;
        this.categoryBackground = categoryThumbnail;
        this.categoryFollowers = categoryFollowers;
        this.categoryPublishers = categoryPublishers;
        //this.isFeatured = 0;
    }


}

class ExampleContentObject {


    /**
     * @author Pim de Witte
     * 6/23/2015
     * gomedal.model
     */


    private final int contentId;
    private final int contentType;
    private final int categoryId;
    private final String contentTitle;

    // original content url
    private final String contentUrl;


    //  thumbnail url
    private final String thumbnail1080p;
    private final String thumbnail720p;
    private final String thumbnail480p;
    private final String thumbnail360p;
    private final String thumbnail240p;
    private final String thumbnail144p;

    private final int videoLengthSeconds;
    private int likes = 0;
    private int views = 0;

    private final String thumbnailUrl;
    private final String contentCredits;

    public ExampleContentObject(
            int categoryId,
            int contentId,
            int contentType,
            String contentTitle,
            String contentUrl,
            String thumbnail1080p,
            String thumbnail720p,
            String thumbnail480p,
            String thumbnail360p,
            String thumbnail240p,
            String thumbnail144p,
            int videoLengthSeconds,
            String credits,
            String thumbnailUrl,
            int contentLikes,
            int contentViews
    ) {
        this.categoryId = categoryId;
        this.contentId = contentId;
        this.contentType = contentType;
        this.contentTitle = contentTitle;
        this.contentUrl = contentUrl;
        this.contentCredits = credits;
        this.thumbnail1080p = thumbnail1080p;
        this.thumbnail720p = thumbnail720p;
        this.thumbnail480p = thumbnail480p;
        this.thumbnail360p = thumbnail360p;
        this.thumbnail240p = thumbnail240p;
        this.thumbnail144p = thumbnail144p;
        this.videoLengthSeconds = videoLengthSeconds;
        this.contentCredits = credits;
        this.thumbnailUrl = thumbnailUrl;
        this.likes = contentLikes;
        this.views = contentViews;
    }


    public String getThumbnail720p() {
        return thumbnail720p;
    }

    public String getThumbnail480p() {
        return thumbnail480p;
    }

    public String getThumbnail360p() {
        return thumbnail360p;
    }

    public String getThumbnail240p() {
        return thumbnail240p;
    }

    public String getThumbnail144p() {
        return thumbnail144p;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public int getContentId() {
        return contentId;
    }

    public int getContentType() {
        return contentType;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getContentCredits() {
        return contentCredits;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getVideoLengthSeconds() {
        return videoLengthSeconds;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }


    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

}


