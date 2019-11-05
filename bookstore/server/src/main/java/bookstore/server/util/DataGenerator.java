package bookstore.server.util;

import bookstore.data.entity.util.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataGenerator {


    private final List<String> streets = Arrays.asList("A random street", "A random road",
            "A city street", "A town street", "A village road");
    private final List<String> cities = Arrays.asList("City A", "City B", "City C", "City D", "City E");
    private final List<String> postcodes = Arrays.asList("A01", "A02", "A03", "B01", "B02", "B03", "C01",
            "C02", "C03", "D01", "D02", "D03");
    private final List<String> publisherNames = Arrays.asList("Publisher A", "Publisher B", "Publisher C",
            "Publisher D", "Publisher E", "Publisher F");

    private final String lorem = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi dignissimos " +
            "laudantium minus, natus tempore veritatis vitae. Architecto consequatur eos laboriosam mollitia natus" +
            " nisi odit, omnis perferendis quis sed totam voluptatum!" +
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad adipisci alias amet ducimus eaque est " +
            "exercitationem incidunt iste maxime, minus molestias nesciunt odit placeat quibusdam quisquam rerum" +
            " sed tempora velit." +
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium, alias ducimus earum error eum" +
            " fugiat fugit illum in magni maxime obcaecati optio pariatur recusandae rem reprehenderit sequi" +
            "temporibus tenetur voluptatibus." +
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam, blanditiis debitis dignissimos, " +
            "eveniet ex laborum mollitia non obcaecati officiis optio perspiciatis ratione veritatis voluptate? " +
            "Autem nesciunt porro reprehenderit tenetur velit?" +
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus aperiam beatae dolorem doloribus, " +
            "eligendi eos incidunt maxime, minus nostrum praesentium quidem quo " +
            "repellat saepe sapiente, sed similique tempore unde velit." +
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab architecto eligendi inventore itaque, iusto " +
            "labore molestiae nemo officiis placeat quae quasi quis quo " +
            "repudiandae saepe sunt tempore temporibus veritatis. Aut!" +
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolorem explicabo " +
            "fugit hic impedit nulla numquam " +
            "officiis optio pariatur quasi tempore. A deleniti fuga magni maiores quo suscipit totam velit vero?";

    private final Random r = new Random();

    @Bean
    public String getStreetName() {
        return streets.get(r.nextInt(streets.size() - 1));
    }

    @Bean
    public String getCity() {
        return cities.get(r.nextInt(cities.size() - 1));
    }

    @Bean
    public String getPostcode() {
        return postcodes.get(r.nextInt(postcodes.size() - 1));
    }

    @Bean
    public String getPublisherName() {
        return publisherNames.get(r.nextInt(publisherNames.size() - 1));
    }

    @Bean
    public String getSynopsis() {
        int maxLength = 400 + r.nextInt(199);
        int end = r.nextInt(lorem.length() - 1);
        int start = (end - maxLength > 0) ? end - maxLength : 0;
        return lorem.substring(start, end);
    }

    //key=book title , value=authors
    @Bean
    public Map<String, Name> getBookAuthors() {
        Map<String, Name> map = new HashMap<>();
        map.put("Hamlet", new Name("William ", "Shakespeare"));
        map.put("To Kill a Mockingbird", new Name("Harper", "Lee"));
        map.put("Invisible Man", new Name("Ralph", "Ellison"));
        map.put("Jane Eyre", new Name("Charlotte", "Bronte"));
        map.put("The Color Purple", new Name("Alice", "Walker"));
        map.put("Madame Bovary", new Name("Gustave", "Flaubert"));
        map.put("The Odyssey", new Name("Homer", ""));
        map.put("The Adventures of Huckleberry Finn", new Name("Mark", "Twain"));
        map.put("Pride and Prejudice", new Name("Jane", "Austen"));
        map.put("Moby-Dick", new Name("Herman", "Melville   "));
        map.put("Great Expectations", new Name("Charles", "Dickens"));
        map.put("The Autobiography of Benjamin Franklin", new Name("Benjamin", "Franklin"));
        map.put("Long Walk to Freedom", new Name("Nelson", "Mandela"));
        map.put("The Diary of a Young Girl", new Name("Anne", "Frank"));
        map.put("Open: An Autobiography by Andre Agassi", new Name("Andre", "Agassi"));
        map.put("Magna Carta: The True Story Behind the Charter", new Name("David", "Starkey"));
        map.put("Napoleon the Great", new Name("Andrew", "Roberts"));
        map.put("Sapiens: A Brief History of Humankind", new Name("Yuval Noah", "Harari"));
        map.put("A Brief History of Time", new Name("Steven", "Hawking"));
        map.put("Heartstone", new Name("Christopher", "Sansom"));
        map.put("The House at Sea's End", new Name("Elly", "Griffiths"));
        map.put("The Eye of the World", new Name("Robert", "Jordan"));
        map.put("The Great Hunt", new Name("Robert", "Jordan"));
        map.put("Dune", new Name("Frank", "Herbert"));
        map.put("The Martian", new Name("Andy", "Weir"));
        map.put("7 Habits of Highly Effective People", new Name("Steven", "Covey"));
        map.put("The Nightingale: A Novel", new Name("Kristin", "Hannah"));
        map.put("Alexander Hamilton", new Name("Ron", "Chernow"));
        map.put("A Man Called Ove: A Novel", new Name("Fredrik", "Backman"));
        map.put("Hillbilly Elegy: A Memoir of a Family and Culture in Crisis", new Name("James", "Vance"));
        map.put("Astrophysics for People in a Hurry", new Name("Neil", "deGrasse Tyson"));
        map.put("Educated: A Memoir", new Name("Tara", "Westover"));
        map.put("How to Win Friends & Influence People", new Name("Dale", "Carneige"));
        map.put("Becoming", new Name("Michelle", "Obama"));
        return map;
    }

    @Bean
    public Map<String, String> getBookImages() {
        Map<String, String> map = new HashMap<>();
        map.put("Hamlet", "books/hamlet.jpg");
        map.put("To Kill a Mockingbird", "books/to_kill_a_mockingbird.jpg");
        map.put("Invisible Man", "books/invisible_man.jpg");
        map.put("Jane Eyre", "books/jane_eyre.jpg");
        map.put("The Color Purple", "books/the_color_purple.jpg");
        map.put("Madame Bovary", "books/madame_bovary.jpg");
        map.put("The Odyssey", "books/the_odyssey.jpg");
        map.put("The Adventures of Huckleberry Finn", "books/huckleberry_finn.jpg");
        map.put("Pride and Prejudice", "books/pride_and_prejudice.jpg");
        map.put("Moby-Dick", "books/moby_dick.jpg");
        map.put("Great Expectations", "books/great_expectations.jpg");
        map.put("The Autobiography of Benjamin Franklin", "books/autobiography_benjamin_franklin.jpg");
        map.put("Long Walk to Freedom", "books/long_walk_to_freedom.jpg");
        map.put("The Diary of a Young Girl", "books/diary_of_a_young_girl.jpg");
        map.put("Open: An Autobiography by Andre Agassi", "books/open_an_autobiography.jpg");
        map.put("Magna Carta: The True Story Behind the Charter", "books/magna_carta.jpg");
        map.put("Napoleon the Great", "books/napoleon_the_great.jpg");
        map.put("Sapiens: A Brief History of Humankind", "books/sapiens_brief_history_humankind.jpg");
        map.put("A Brief History of Time", "books/brief_history_of_time.jpg");
        map.put("Heartstone", "books/heartstone.jpg");
        map.put("The House at Sea's End", "books/house_at_sea's_end.jpg");
        map.put("The Eye of the World", "books/eye_of_the_world.jpg");
        map.put("The Great Hunt", "books/the_great_hunt.jpg");
        map.put("Dune", "books/dune.jpg");
        map.put("A Game of Thrones", "books/game_of_thrones.jpg");
        map.put("The Martian", "books/the_martian.jpg");
        map.put("7 Habits of Highly Effective People", "books/7_habits.jpg");
        map.put("The Nightingale: A Novel", "books/nightingale.jpg");
        map.put("Alexander Hamilton", "books/hamilton.jpg");
        map.put("A Man Called Ove: A Novel", "books/ove.jpg");
        map.put("Hillbilly Elegy: A Memoir of a Family and Culture in Crisis", "books/hillbilly_elegy.jpg");
        map.put("Astrophysics for People in a Hurry", "books/astrophysics.jpg");
        map.put("Educated: A Memoir", "books/educated.jpg");
        map.put("How to Win Friends & Influence People", "books/influence_people.jpg");
        map.put("Becoming", "books/becoming.jpg");
        return map;
    }

    @Bean
    public BigDecimal setPrice() {
        return BigDecimal.valueOf(setAmount());
    }

    private Double setAmount() {
        Random r = new Random();
        String string = (r.nextInt(20) + 1) + "." + r.nextInt(99) + 1;
        return Double.parseDouble(string);
    }

    @Bean
    public long setQuantity() {
        Random r = new Random();
        return r.nextInt(10) + 1;
    }
}
