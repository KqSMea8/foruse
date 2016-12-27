package hbasetest.testone;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created with foruse.
 * User: hzyuqi1
 * Date: 2016/11/4
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */

public class TestOne {

    private static final Logger logger = LoggerFactory.getLogger(TestOne.class);
    private static HBaseAdmin hBaseAdmin;

    static {
        try{
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum","node1");

            System.out.println("before");
            hBaseAdmin = new HBaseAdmin(configuration);
            System.out.println("hBaseAdmin:" + hBaseAdmin);
        }catch (Exception e){
            System.out.println(e);
            //logger.error("get error {}",e);
        }
    }

    public static void main(String [] args){

        /*
        try {
            Configuration configuration = HBaseConfiguration.create();
            //configuration.addResource("conf.properties"); //只能add xml 文件
            configuration.set("hbase.zookeeper.quorum", "inspur251.photo.163.org,inspur252.photo.163.org,inspur253.photo.163.org,inspur254.photo.163.org,inspur255.photo.163.org");

            HBaseAdmin admin = new HBaseAdmin(configuration);

            HTableDescriptor tableDescriptor = new HTableDescriptor("test1");

            tableDescriptor.addFamily(new HColumnDescriptor("personal"));
            tableDescriptor.addFamily(new HColumnDescriptor("professional"));
            admin.createTable(tableDescriptor);
            System.out.println("create tabe success!!");
        }catch (MasterNotRunningException ee){
            System.out.print("get error: " + ee);
            logger.error("get error: {}",ee);
        }catch (Exception e){
            logger.error("get error: {}",e);
        }*/


        String [] columns = new String[]{"age","name"};
        try {
            createTable("good", columns);
        }catch (Exception e){
            System.out.println(e);
            logger.error("get error {}",e);
        }

        System.out.println("Hello,world!");


    }


    private static void createTable(String tableName,String [] columns) throws IOException{
        System.out.println("iii");
        dropTable(tableName);
        System.out.println("222");
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);

        for (String column : columns){
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(column);
            hTableDescriptor.addFamily(columnDescriptor);
        }

        System.out.println("3");

        hBaseAdmin.createTable(hTableDescriptor);
        System.out.println("4");
        System.out.println("create table success!");

    }


    private static void dropTable(String tableName){
        try {
            if (hBaseAdmin.tableExists(tableName)) {
                System.out.println("table exist");
                hBaseAdmin.disableTable(tableName);
                hBaseAdmin.deleteTable(tableName);
            }
        }catch (Exception e){
            logger.error("get error {}",e);
        }
    }


}
