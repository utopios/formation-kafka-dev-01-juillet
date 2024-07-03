package utopios.net;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CustomTrackingConnector extends SourceConnector {
    private static final Logger log = LoggerFactory.getLogger(CustomTrackingConnector.class);
    private Map<String, String> config;

    @Override
    public void start(Map<String, String> props) {
        this.config = props;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return CustomTrackingTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        return List.of(config);
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return new ConfigDef()
                .define("api.url", ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "API URL");
    }

    @Override
    public String version() {
        return "1.0";
    }
}
