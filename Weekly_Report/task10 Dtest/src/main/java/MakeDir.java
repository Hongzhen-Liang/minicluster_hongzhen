import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
public class MakeDir {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        String uri = "hdfs://localhost:8020/";
        //FileSystem hdfs = FileSystem.get(new URI(hdfsPath), conf);
        FileSystem hdfs = FileSystem.get(URI.create(uri),conf);
        String newDir = "/hdfstest";
        boolean result = hdfs.mkdirs(new Path(newDir));
        if (result) {
            System.out.println("Success!");
        }else {
            System.out.println("Failed!");
        }
    }
}
