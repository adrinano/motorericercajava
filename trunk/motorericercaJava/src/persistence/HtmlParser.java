/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.htmlparser.*;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.NodeTreeWalker;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;


/**
 *
 * @author palla
 */
public class HtmlParser {

    public HtmlParser(){

    }

    public void Parser(File file, int number) throws FileNotFoundException, IOException{

        //FileOutputStream fos = new FileOutputStream("xml_To_Index/html_"+ number + ".xml");
        //String html = getHtml(file);
        //System.out.println(html);
        try {
            Parser parser = new Parser(file.getAbsolutePath() + ".html");
            parser.setEncoding("ISO-8859-1");
            //PrintStream ps = new PrintStream(fos);
            //ps.print("<?xml version="+'"'+"1.0"+'"'+ " encoding="+'"'+"utf-8"+'"'+"?>");
            NodeFilter filter = new NodeClassFilter();
            NodeList list = parser.parse(filter);
            //System.out.println("List: " + list.elementAt(0).toHtml());

            NodeTreeWalker ntw = new NodeTreeWalker(list.elementAt(0));
            Node node = ntw.getRootNode();

            String text = node.getText();
            while (!text.contains("table")){
                    node = ntw.nextNode();
                    text = node.getText();
            }
            System.out.println("text: " + text);

            NodeList nodeList = node.getChildren();

            for (int i=0; i<nodeList.size(); i++){
                System.out.println("NodeList " + i +": " + nodeList.elementAt(i).toPlainTextString());
            }

        } catch (ParserException ex) {
            Logger.getLogger(HtmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }finally{

        //fos.close();
        }

    }

    /**
     * La funzione non √® utilizzata attualmente
     *
     * Dato il file html restituisce il codice html
     * @param file
     * @return
     */
    private String getHtml(File file) {
        String html = null;
        System.out.println(file.exists());
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsolutePath() + ".html"));
            String str;
            while ((str = in.readLine()) != null) {
                html = html + str;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }

}
