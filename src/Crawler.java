import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

    /**
     * URL for the top selling free music and audio apps in Google Play
     */
    public final static String GOOGLE_PLAY_MUSIC_AND_AUDIO_TOP_FREE_URL = "https://play.google.com/store/apps/category/MUSIC_AND_AUDIO/collection/topselling_free";

    /**
     * URL for the top selling free music games in Google Play
     */
    public final static String GOOGLE_PLAY_MUSIC_GAMES_TOP_FREE_URL = "https://play.google.com/store/apps/category/GAME_MUSIC/collection/topselling_free";

    /**
     * Path where the Excel file will be created if it doesn't exist, or overwritten if it does.
     */
    public final static String EXCEL_FILE_PATH = "docs/gatheredData.xlsx";
    
    /**
     * Contains all the apps that have been researched
     */
    private static ArrayList<MobileApp> apps;

    /**
     * Extracts links to the top Music and Audio free applications in Google Play. Then,
     * accesses one every 10 seconds and extracts title, description, number of ratings,
     * average rating, number of 5-star rating and number of 4-star rating of each of them.
     * Finally, saves all information in an Excel file at EXCEL_FILE_PATH. 
     * A 10 second wait is done to avoid Google detecting the crawler as an attacker or
     * something similar and blocking it.
     * @throws IOException if there's an error accessing EXCEL_FILE_PATH or overwriting it, 
     * 						e.g. another process is using it.
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        apps = new ArrayList<MobileApp>();
        Document doc = Jsoup.connect(GOOGLE_PLAY_MUSIC_AND_AUDIO_TOP_FREE_URL).timeout(0).get();

        HashSet<String> hrefs = new HashSet<String>();

        Elements cardClicks = doc.getElementsByClass("card-click-target");

        for (Element e : cardClicks)
        {
            hrefs.add("https://play.google.com/" + e.attr("href").toString());
        }
        
        int processedApps = 0;
        for (String link : hrefs)
        {
            Thread.sleep(10000);
            MobileApp app = new MobileApp();
            doc = Jsoup.connect(link).timeout(0).get();
            String title = doc.getElementsByClass("id-app-title").text();
            app.setTitle(title);
            app.setDescription(doc.select("[itemprop='description']").text());
            app.setNumberOfRatings(doc.getElementsByClass("rating-count").text());
            app.setRating(doc.getElementsByClass("score").text());
            app.setNumberOf4StarRatings(doc.getElementsByClass("rating-bar-container four").text().split(" ")[1]);
            app.setNumberOf5StarRatings(doc.getElementsByClass("rating-bar-container five").text().split(" ")[1]);
            app.setLink(link);
            Elements changes = doc.getElementsByClass("recent-change");

            for (Element e : changes)
            {
                app.addRecentChange(e.text());
            }

            apps.add(app);
            if (apps.size() > 100)
            {
                break;
            }
            System.out.println(app.toString());
            processedApps++;
            System.out.println("So far, " + processedApps + " apps have been processed");
        }

        generateExcel();
    }

    private static void generateExcel() throws FileNotFoundException, IOException {
        Workbook wb = new XSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream(EXCEL_FILE_PATH);
        Sheet sheet1 = wb.createSheet("Data");

        Row row = sheet1.createRow(0);
        row.createCell(0).setCellValue("Title");
        row.createCell(1).setCellValue("Link");
        row.createCell(2).setCellValue("Rating");
        row.createCell(3).setCellValue("# of Ratings");
        row.createCell(4).setCellValue("5 Star-Ratings");
        row.createCell(5).setCellValue("4 Star-Ratings");

        for (int i = 0; i < apps.size(); i++)
        {
            MobileApp app = apps.get(i);
            row = sheet1.createRow(i+1);
            row.createCell(0).setCellValue(app.getTitle());
            row.createCell(1).setCellValue(app.getLink());
            row.createCell(2, CellType.NUMERIC).setCellValue(app.getRating());
            row.createCell(3, CellType.NUMERIC).setCellValue(Integer.valueOf(app.getNumberOfRatings().replace(",", "")));
            row.createCell(4, CellType.NUMERIC).setCellValue(Integer.valueOf(app.getNumberOf5StarRatings().replace(",", "")));
            row.createCell(5, CellType.NUMERIC).setCellValue(Integer.valueOf(app.getNumberOf4StarRatings().replace(",", "")));
        }

        row = sheet1.createRow(apps.size()+2);
        row.createCell(0).setCellValue("# of Apps");
        Cell cell = row.createCell(1, CellType.NUMERIC);
        cell.setCellValue(apps.size());

        row = sheet1.createRow(apps.size()+3);
        row.createCell(0).setCellValue("Creation date");
        cell = row.createCell(1, CellType.STRING);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        cell.setCellValue(dateFormat.format(date));
        for (int i = 0; i < 6; i++)
        {
            sheet1.autoSizeColumn(i);
        }

        wb.write(fileOut);
        fileOut.close();
        System.out.println("Successfully created Excel file at " + EXCEL_FILE_PATH);
    }
}
