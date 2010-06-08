/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import modello.ComparatoreSitiBean;
import modello.SitoBean;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author palla
 */
public class XMLFunction {
    private File file;

    public XMLFunction() {
        this.file = new File("webapps/sherlockTux/WEB-INF/config/siteUrl.xml");
    }



    public boolean addSiteToXML(SitoBean sito){

        boolean verifica = false;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);


            Node corsi = doc.getFirstChild();

            Node corso = doc.createElement("corso");
            NamedNodeMap corsoAttr = corso.getAttributes();
            Attr id = doc.createAttribute("id");
            id.setValue(sito.getId());
            corsoAttr.setNamedItem(id);

            Node name = doc.createElement("name");
            name.setTextContent(sito.getMateria());

            Node url = doc.createElement("url");
            url.setTextContent(sito.getUrl().toString());

            Node uspsw = doc.createElement("uspsw");
            uspsw.setTextContent(sito.getPassword());

            corso.appendChild(name);
            corso.appendChild(url);
            corso.appendChild(uspsw);

            corsi.appendChild(corso);

            //setting up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            //generating string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            //Saving the XML content to File
            OutputStream f0;
            byte buf[] = xmlString.getBytes();
            f0 = new FileOutputStream(file);
            for(int i=0;i<buf .length;i++) {
                f0.write(buf[i]);
            }
            f0.close();
            buf = null;

            verifica = true;

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verifica;

    }

    public List<SitoBean> getSiteList(){
        List<SitoBean> lst = new LinkedList();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("corso");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                SitoBean sb = new SitoBean();
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;
                    NodeList NameElmntLst = fstElmnt.getElementsByTagName("name");
                    Element fstNmElmnt = (Element) NameElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    sb.setMateria(((Node) fstNm.item(0)).getNodeValue());

                    NodeList urlElmntLst = fstElmnt.getElementsByTagName("url");
                    Element lstUrlElmnt = (Element) urlElmntLst.item(0);
                    NodeList lstUrl = lstUrlElmnt.getChildNodes();
                    sb.setUrl(new URL(((Node) lstUrl.item(0)).getNodeValue()));

                    NodeList pswElmntLst = fstElmnt.getElementsByTagName("uspsw");
                    Element lstPswElmnt = (Element) pswElmntLst.item(0);
                    NodeList lstPsw = lstPswElmnt.getChildNodes();
                    sb.setPassword(((Node) lstPsw.item(0)).getNodeValue());
                    
                    sb.setId(fstElmnt.getAttribute("id"));

                    lst.add(sb);
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        Collections.sort(lst, new ComparatoreSitiBean());
        return lst;
    }
}
