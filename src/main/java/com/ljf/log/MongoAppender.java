package com.ljf.log;

/**
 * Created by lujiafeng on 2018/8/6.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
/**
 * log4j提供的输出器实现自Appender接口，要自定义Appender输出到MongoDB，
 * 只需要继承AppenderSkeleton类，并实现几个方法即可完成
 */

public class MongoAppender extends AppenderSkeleton {
    private MongoClient mongoClient;  //mongodb的连接客户端
    private MongoDatabase mongoDatabase;  //记录日志的数据库
    private MongoCollection<BasicDBObject> logsCollection;  //记录日志的集合

    private String connectionUrl;  //连接mongodb的串
    private String databaseName;   //数据库名
    private String collectionName; //集合名


    //重写append函数
    @Override
    protected void append(LoggingEvent loggingEvent) {  //loggingEvent提供getMessage()函数来获取日志消息
        if(mongoDatabase == null) {
            MongoClientURI connectionString = new MongoClientURI(connectionUrl);
            mongoClient = new MongoClient(connectionString);
            mongoDatabase = mongoClient.getDatabase(databaseName);
            logsCollection = mongoDatabase.getCollection(collectionName, BasicDBObject.class);
        }
        logsCollection.insertOne((BasicDBObject) loggingEvent.getMessage());

    }

    @Override
    public void close() {
        if(mongoClient != null) {
            mongoClient.close();
        }
    }

    @Override
    public boolean requiresLayout() { return false; }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
