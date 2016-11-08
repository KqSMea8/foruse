package kafkatest;

import com.google.common.collect.Lists;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;

/**
 * Created with foruse.
 * User: hzyuqi1
 * Date: 2016/11/2
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */

public class TestTwo {

    //kafa test
    private static Logger logger = LoggerFactory.getLogger(TestTwo.class);

    public static void main(String [] args){

        //String broker = "kafka0.xs.163.org:9092,kafka1.xs.163.org:9092,kafka2.xs.163.org:9092,kafka3.xs.163.org:9092";

        String broker = "db-180.photo.163.org:9092,db-180.photo.163.org:9093,db-180.photo.163.org:9094";

        Properties properties = new Properties();
        properties.put("metadata.broker.list",broker);

        properties.put("serializer.class","kafka.serializer.StringEncoder");
        properties.put("key.serializer.class" , "kakfa.serializer.StringEncoder");
        properties.put("group.id","test");

        //properties.put("partitioner.class","kafka.kafkatest.TestOne");

        properties.put("request.required.acks",1);

        properties.put("bootstrap.servers",broker);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        /*kafkaConsumer.subscribe(Lists.newArrayList("test"), new ConsumerRebalanceListener() {
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {

            }

            public void onPartitionsAssigned(Collection<TopicPartition> collection) {

            }
        });*/



        TopicPartition topicPartition = new TopicPartition("test",0);
        kafkaConsumer.assign(Lists.<TopicPartition>newArrayList(topicPartition));
        //kafkaConsumer.seek(topicPartition,100);
        //kafkaConsumer.subscribe(Lists.<String>newArrayList("test"));

        kafkaConsumer.seekToBeginning(topicPartition);
        System.out.println(kafkaConsumer.assignment());

        kafkaConsumer.partitionsFor("test");
        for (PartitionInfo partitionInfo : kafkaConsumer.partitionsFor("test")){
            System.out.print(partitionInfo.toString());
        }

        while(true){
            ConsumerRecords<String,String> records = kafkaConsumer.poll(1000);
            for(ConsumerRecord<String,String> record : records){
                System.out.println("partition="+record.partition());
                System.out.println("offset="+record.offset());
                System.out.println("value="+record.value());
                System.out.println("topic="+record.topic());
                System.out.println("key="+record.key());
            }
        }

    }

}