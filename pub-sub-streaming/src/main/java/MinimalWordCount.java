import java.util.Arrays;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import  org.apache.beam.sdk.transforms.windowing.Window;
import  org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.joda.time.Duration;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.POutput;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
class JProc1 extends DoFn<KV<String, Iterable<Integer>>,String> {
  @ProcessElement
  public void processElement(@Element KV<String,Iterable<Integer>> kv, OutputReceiver<String> out) { 
    kt.funcs.kproc4(kv, out); 
    //String key = kv.getKey().toString();
    //out.output(key);
  }
}

public class MinimalWordCount {
  public static void main(String[] args) {
    kt.funcs.testCall();
    DataflowPipelineOptions options = PipelineOptionsFactory.create().as(DataflowPipelineOptions.class);
    options.setProject("dena-ai-training-16-gcp");
    options.setStagingLocation("gs://abc-tmp/STAGING");
		options.setTempLocation("gs://abc-tmp/tmp");
		options.setRunner(DataflowRunner.class);
		options.setStreaming(true);
    options.setJobName("streamingJob6");

    Pipeline p = Pipeline.create(options);
    PCollection p1 = p.apply(PubsubIO.readStrings().fromSubscription("projects/dena-ai-training-16-gcp/subscriptions/sub3"))
        .apply(Window.<String>into(FixedWindows.of(Duration.standardMinutes(1))));
    POutput p2 = p1.apply( TextIO.write()
                .withWindowedWrites()
                .withNumShards(1)
                .to("gs://abc-tmp/OUTPUT") );
        //.apply( ParDo.of(new kt.KProc1()))
        //.apply( Filter.by( (String chars) -> kt.funcs.filter1(chars) ))
        //.apply( ParDo.of(new kt.KProc2()))
        //.apply( GroupByKey.create())
        //.apply( ParDo.of(new JProc1()) );
    //POutput p2 = p1.apply( TextIO.write().to("gs://abc-wild/OUTPUT2") );
    p.run().waitUntilFinish();
    
  }
}
