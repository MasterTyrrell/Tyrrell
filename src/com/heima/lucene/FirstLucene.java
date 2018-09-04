package com.heima.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class FirstLucene {
	@Test
	public void test1() throws IOException{
		//索引库存放位置
		Directory directory=FSDirectory.open(new File("D:\\temp\\index"));
		//分析器
		Analyzer analyzer=new StandardAnalyzer();
		IndexWriterConfig config=new IndexWriterConfig(Version.LATEST,analyzer);
		//创建IndexWriter对象
		IndexWriter iw=new IndexWriter(directory,config);
		File f=new File("F:\\ebook");
		File[] list=f.listFiles();
		Document document=new Document();
		for(File file:list){
			//文件名
			document=new Document();
			String name=file.getName();
			Field fileNameField=new TextField("fileNameField",name,Store.YES);
			//文件大小	
			Long size=FileUtils.sizeOf(file);
			Field fileSizeField=new LongField("fileSizeField",size,Store.YES);
			//文件路径
			String path=file.getPath();
			Field filePathField=new StoredField("filePath",path);
			//文件内容
			String fileContent=FileUtils.readFileToString(file);
			Field fileContentField=new TextField("fileContentField",fileContent,Store.NO);
			document.add(fileNameField);
			document.add(fileSizeField);
			document.add(filePathField);
			document.add(fileContentField);
			iw.addDocument(document);
		}
		iw.close();
	}
}
