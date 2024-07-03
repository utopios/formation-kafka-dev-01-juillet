package utopios.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomTrackingTask extends SourceTask {
    private static final Logger log = LoggerFactory.getLogger(CustomTrackingTask.class);
    private String apiUrl;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void start(Map<String, String> props) {
        this.apiUrl = props.get("api.url");
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        List<SourceRecord> records = new ArrayList<>();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                JsonNode data = objectMapper.readTree(inline);
                for (JsonNode node : data) {

                    Map<String, String> sourcePartition = new HashMap<>();
                    Map<String, String> sourceOffset = new HashMap<>();


                    String idColis = node.get("trackingId").asText();
                    String timestampScan = node.get("scanTimestamp").asText();
                    String lieuScan = node.get("scanLocation").asText();
                    String etatColis = node.get("packageStatus").asText();

                    Map<String, Object> value = new HashMap<>();
                    value.put("idColis", idColis);
                    value.put("timestampScan", timestampScan);
                    value.put("lieuScan", lieuScan);
                    value.put("etatColis", etatColis);

                    SourceRecord record = new SourceRecord(
                            sourcePartition,
                            sourceOffset,
                            "tracking-events",
                            Schema.STRING_SCHEMA,
                            idColis,
                            Schema.BYTES_SCHEMA,
                            value
                    );
                    records.add(record);
                }
            }
        } catch (IOException e) {
            log.error("Error while polling data from API", e);
        }

        return records;
    }

    @Override
    public void stop() {
    }

    @Override
    public String version() {
        return "1.0";
    }
}
