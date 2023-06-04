package src;

import java.io.*;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//compress file
import java.util.zip.DeflaterOutputStream;


public class FileIO {

    FileWriter fw;
    String filename;

    FileIO() {
        filename = "fw-polygon-" + Instant.now().toEpochMilli() + ".xml";
        ;
    }

    FileIO(String fn) {
        filename = fn;
    }


    public void write(ArrayList<MyPolygon> polygonList, ArrayList<MyLine> myLineList,ArrayList<MyPoint> myPointList) {
        try {
            File inputFile = new File(System.getProperty("user.dir") + "/Geogebra/templateFile/" + filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            // Accessing root element
            Element root = document.getDocumentElement();
            //mypolygon list
            for (int i = 0; i < polygonList.size(); i++) {
                MyPolygon p = polygonList.get(i);
                for (int j = 0; j < p.xCords.size(); j++) {
                    //fw.write("" + i + " " + p.xCords.get(j) + " " + p.yCords.get(j) + "\n");
                    //element tag start
                    Element newElement = document.createElement("element");
                    newElement.setAttribute("type", "point");
                    newElement.setAttribute("label", "A" + p.xCords.get(j) + p.yCords.get(j));

                    //show
                    Element newShow = document.createElement("show");
                    newShow.setAttribute("object", "true");
                    newShow.setAttribute("label", "true");
                    newShow.setAttribute("ev", "4");
                    newElement.appendChild(newShow);

                    //objColor
                    Element objColor = document.createElement("objColor");
                    objColor.setAttribute("r", "21");
                    objColor.setAttribute("g", "101");
                    objColor.setAttribute("b", "192");
                    objColor.setAttribute("alpha", "0");
                    newElement.appendChild(objColor);
                    //layer
                    Element layer = document.createElement("layer");
                    layer.setAttribute("val", "0");
                    newElement.appendChild(layer);
                    //labelMode
                    Element labelMode = document.createElement("labelMode");
                    labelMode.setAttribute("val", "0");
                    newElement.appendChild(labelMode);

                    //animation
                    Element animation = document.createElement("animation");
                    animation.setAttribute("step", "0.1");
                    animation.setAttribute("speed", "1");
                    animation.setAttribute("type", "1");
                    animation.setAttribute("playing", "false");
                    newElement.appendChild(animation);
                    //pointSize
                    Element pointSize = document.createElement("pointSize");
                    pointSize.setAttribute("val", "5");
                    newElement.appendChild(pointSize);
                    //pointStyle
                    Element pointStyle = document.createElement("pointStyle");
                    pointStyle.setAttribute("val", "0");
                    newElement.appendChild(pointStyle);
                    //coords tag
                    Element newCoords = document.createElement("coords");
                    newCoords.setAttribute("x", p.xCords.get(j).toString());
                    newCoords.setAttribute("y", String.valueOf(720 - p.yCords.get(j)));
                    newCoords.setAttribute("z", "1");
                    newElement.appendChild(newCoords);
                    //element tag end

                    //append element tag
                    (root.getElementsByTagName("construction").item(0)).appendChild(newElement);
                    //System.out.println("Print polygon vertices "+ j);

                }
                //command
                System.out.println("Print polygon " + i);
                Element newCommand = document.createElement("command");
                newCommand.setAttribute("name", "Polygon");

                Element commandInput = document.createElement("input");
                for (int j = 0; j < p.xCords.size(); j++) {
                    //A j add different unique string
                    commandInput.setAttribute("a" + j, "A" + p.xCords.get(j)+p.yCords.get(j));
                }
                newCommand.appendChild(commandInput);

                Element commandOutput = document.createElement("output");
                commandOutput.setAttribute("a0", "poly" + i);
                newCommand.appendChild(commandOutput);
                //append command tag
                (root.getElementsByTagName("construction").item(0)).appendChild(newCommand);
            }
            //mylinelist
            for (int i = 0; i < myLineList.size(); i++) {
                MyLine p = myLineList.get(i);
//            if (p.get_P1()!=null){
//                System.out.println("p1 not null");
//            }
//            if (p.get_P2()!=null){
//                System.out.println("p2 not null");
//            }
                int count = 0;
                for (int j = 0; j < 2; j++) {

                    //System.out.println("m heres");
                    Element newElementLine = document.createElement("element");
                    newElementLine.setAttribute("type", "point");
                    if(j==0)
                    newElementLine.setAttribute("label", "L" + p.get_P2().get_x());
                    if(j==1) newElementLine.setAttribute("label", "L" + p.get_P2().get_y());

                    //show
                    Element newShow = document.createElement("show");
                    newShow.setAttribute("object", "true");
                    newShow.setAttribute("label", "true");
//                    newShow.setAttribute("ev", "4");
                    newElementLine.appendChild(newShow);

                    //objColor
                    Element objColor = document.createElement("objColor");
                    objColor.setAttribute("r", "21");
                    objColor.setAttribute("g", "101");
                    objColor.setAttribute("b", "192");
                    objColor.setAttribute("alpha", "0");
                    newElementLine.appendChild(objColor);
                    //layer
                    Element layer = document.createElement("layer");
                    layer.setAttribute("val", "0");
                    newElementLine.appendChild(layer);
                    //labelMode
                    Element labelMode = document.createElement("labelMode");
                    labelMode.setAttribute("val", "0");
                    newElementLine.appendChild(labelMode);

                    //animation
                    Element animation = document.createElement("animation");
                    animation.setAttribute("step", "0.1");
                    animation.setAttribute("speed", "1");
                    animation.setAttribute("type", "1");
                    animation.setAttribute("playing", "false");
                    newElementLine.appendChild(animation);
                    //pointSize
                    Element pointSize = document.createElement("pointSize");
                    pointSize.setAttribute("val", "5");
                    newElementLine.appendChild(pointSize);
                    //pointStyle
                    Element pointStyle = document.createElement("pointStyle");
                    pointStyle.setAttribute("val", "0");
                    newElementLine.appendChild(pointStyle);
                    count++;
                    //coords tag
                    System.out.println("count "+count);
                    if (count == 1) {
                        System.out.println("p1 not null");
                        Element newCoords = document.createElement("coords");
                        newCoords.setAttribute("x", String.valueOf(p.get_P1().get_x()));
                        newCoords.setAttribute("y", String.valueOf(720 - p.get_P1().get_y()));
                        newCoords.setAttribute("z", "1");
                        newElementLine.appendChild(newCoords);
                    }
                    if (count == 2) {
                        System.out.println("p2 not null");
                        Element newCoords = document.createElement("coords");
                        newCoords.setAttribute("x", String.valueOf(p.get_P2().get_x()));
                        newCoords.setAttribute("y", String.valueOf(720 - p.get_P2().get_y()));
                        newCoords.setAttribute("z", "1");
                        newElementLine.appendChild(newCoords);
                        //System.out.println("new element "+newCoords);
                    }
                    (root.getElementsByTagName("construction").item(0)).appendChild(newElementLine);
                }
                //command
                //System.out.println("Print polygon " + i);
                Element newCommand = document.createElement("command");
                newCommand.setAttribute("name", "Segment");

                Element commandInput = document.createElement("input");
                for (int j = 0; j < 2; j++) {
                    if(j==0)
                    commandInput.setAttribute("a" + j, "L" + p.get_P2().get_x());
                    if(j==1)commandInput.setAttribute("a" + j, "L" + p.get_P2().get_y());
                }


               // commandInput.setAttribute("a" + (i+1), "L" + p.get_P2().get_x());
                newCommand.appendChild(commandInput);

                Element commandOutput = document.createElement("output");
                commandOutput.setAttribute("a0", "seg" + i);
                newCommand.appendChild(commandOutput);
                //append command tag
                (root.getElementsByTagName("construction").item(0)).appendChild(newCommand);
                System.out.println("p x " + p.get_P1().get_x() + " y " + p.get_P1().get_y());
            }
            //myPointlist
            for (int i = 0; i < myPointList.size(); i++) {
                MyPoint p = myPointList.get(i);
                //System.out.println("p value "+p.get_x());

                    Element newElementLine = document.createElement("element");
                    newElementLine.setAttribute("type", "point");
                    newElementLine.setAttribute("label", "P" + p.get_x()+p.get_y());

                    //show
                    Element newShow = document.createElement("show");
                    newShow.setAttribute("object", "true");
                    newShow.setAttribute("label", "true");
                    newElementLine.appendChild(newShow);

                    //objColor
                    Element objColor = document.createElement("objColor");
                    objColor.setAttribute("r", "21");
                    objColor.setAttribute("g", "101");
                    objColor.setAttribute("b", "192");
                    objColor.setAttribute("alpha", "0");
                    newElementLine.appendChild(objColor);
                    //layer
                    Element layer = document.createElement("layer");
                    layer.setAttribute("val", "0");
                    newElementLine.appendChild(layer);
                    //labelMode
                    Element labelMode = document.createElement("labelMode");
                    labelMode.setAttribute("val", "0");
                    newElementLine.appendChild(labelMode);

                    //animation
                    Element animation = document.createElement("animation");
                    animation.setAttribute("step", "0.1");
                    animation.setAttribute("speed", "1");
                    animation.setAttribute("type", "1");
                    animation.setAttribute("playing", "false");
                    newElementLine.appendChild(animation);
                    //pointSize
                    Element pointSize = document.createElement("pointSize");
                    pointSize.setAttribute("val", "5");
                    newElementLine.appendChild(pointSize);
                    //pointStyle
                    Element pointStyle = document.createElement("pointStyle");
                    pointStyle.setAttribute("val", "0");
                    newElementLine.appendChild(pointStyle);

                        Element newCoords = document.createElement("coords");
                        newCoords.setAttribute("x", String.valueOf(p.get_x()));
                        newCoords.setAttribute("y", String.valueOf(720 - p.get_y()));
                        newCoords.setAttribute("z", "1");
                        newElementLine.appendChild(newCoords);

                    (root.getElementsByTagName("construction").item(0)).appendChild(newElementLine);
            }
            // Saving changes to a new XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("./Geogebra/savedFile/geogebra.xml"));
            transformer.transform(source, result);
            System.out.println("XML file updated successfully.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //    public void writeTwo(ArrayList<MyPointSan> myPointList){
//
//    }
//    public void writeLine(ArrayList<MyLine> myLineList) {
//        try {
//            File inputFile = new File(System.getProperty("user.dir") + "/Geogebra/templateFile/" + filename);
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(inputFile);
//            // Accessing root element
//            Element root = document.getDocumentElement();
//            // Element nodeList = root.getElementsByTagName("construction");
//            System.out.println("m line list " + myLineList.size());
//            for (int i = 0; i < myLineList.size(); i++) {
//                MyLine p = myLineList.get(i);
////            if (p.get_P1()!=null){
////                System.out.println("p1 not null");
////            }
////            if (p.get_P2()!=null){
////                System.out.println("p2 not null");
////            }
//                int count = 0;
//                for (int j = 0; j < 2; j++) {
//
//
//                    Element newElement = document.createElement("element");
//                    newElement.setAttribute("type", "point");
//                    newElement.setAttribute("label", "A" + p.get_P1().get_x());
//
//                    //show
//                    Element newShow = document.createElement("show");
//                    newShow.setAttribute("object", "true");
//                    newShow.setAttribute("label", "true");
//                    newShow.setAttribute("ev", "4");
//                    newElement.appendChild(newShow);
//
//                    //objColor
//                    Element objColor = document.createElement("objColor");
//                    objColor.setAttribute("r", "21");
//                    objColor.setAttribute("g", "101");
//                    objColor.setAttribute("b", "192");
//                    objColor.setAttribute("alpha", "0");
//                    newElement.appendChild(objColor);
//                    //layer
//                    Element layer = document.createElement("layer");
//                    layer.setAttribute("val", "0");
//                    newElement.appendChild(layer);
//                    //labelMode
//                    Element labelMode = document.createElement("labelMode");
//                    labelMode.setAttribute("val", "0");
//                    newElement.appendChild(labelMode);
//
//                    //animation
//                    Element animation = document.createElement("animation");
//                    animation.setAttribute("step", "0.1");
//                    animation.setAttribute("speed", "1");
//                    animation.setAttribute("type", "1");
//                    animation.setAttribute("playing", "false");
//                    newElement.appendChild(animation);
//                    //pointSize
//                    Element pointSize = document.createElement("pointSize");
//                    pointSize.setAttribute("val", "5");
//                    newElement.appendChild(pointSize);
//                    //pointStyle
//                    Element pointStyle = document.createElement("pointStyle");
//                    pointStyle.setAttribute("val", "0");
//                    newElement.appendChild(pointStyle);
//                    count++;
//                    //coords tag
//                    if (count == 1) {
//                        System.out.println("p1 not null");
//                        Element newCoords = document.createElement("coords");
//                        newCoords.setAttribute("x", String.valueOf(p.get_P1().get_x()));
//                        newCoords.setAttribute("y", String.valueOf(720 + p.get_P1().get_y()));
//                        newCoords.setAttribute("z", "1");
//                        newElement.appendChild(newCoords);
//                    }
//                    if (count == 2) {
//                        System.out.println("p2 not null");
//                        Element newCoords = document.createElement("coords");
//                        newCoords.setAttribute("x", String.valueOf(p.get_P2().get_x()));
//                        newCoords.setAttribute("y", String.valueOf(720 + p.get_P2().get_y()));
//                        newCoords.setAttribute("z", "1");
//                        newElement.appendChild(newCoords);
//                    }
//                }
//                //command
//                System.out.println("Print polygon " + i);
//                Element newCommand = document.createElement("command");
//                newCommand.setAttribute("name", "Segment");
//
//                Element commandInput = document.createElement("input");
//                commandInput.setAttribute("a" + i, "A" + p.get_P1().get_x());
//                commandInput.setAttribute("a" + (i+1), "A" + p.get_P2().get_y());
//                newCommand.appendChild(commandInput);
//
//                Element commandOutput = document.createElement("output");
//                commandOutput.setAttribute("a0", "seg" + i);
//                newCommand.appendChild(commandOutput);
//                //append command tag
//                (root.getElementsByTagName("construction").item(0)).appendChild(newCommand);
//                System.out.println("p x " + p.get_P1().get_x() + " y " + p.get_P1().get_y());
//            }
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            DOMSource source = new DOMSource(document);
//            StreamResult result = new StreamResult(new File("./Geogebra/savedFile/geogebra.xml"));
//            transformer.transform(source, result);
//            System.out.println("XML file updated successfully.");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//    public void writePoint(ArrayList<MyPoint> myPointList) {
//        try {
//            File inputFile = new File(System.getProperty("user.dir") + "/Geogebra/templateFile/" + filename);
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(inputFile);
//            // Accessing root element
//            Element root = document.getDocumentElement();
//            // Element nodeList = root.getElementsByTagName("construction");
//
//            for (int i = 0; i < polygonList.size(); i++) {
//                MyPolygon p = polygonList.get(i);
//                for (int j = 0; j < p.xCords.size(); j++) {
//                    //fw.write("" + i + " " + p.xCords.get(j) + " " + p.yCords.get(j) + "\n");
//                    //element tag start
//                    Element newElement = document.createElement("element");
//                    newElement.setAttribute("type", "point");
//                    newElement.setAttribute("label", "A" + p.xCords.get(j));
//
//                    //show
//                    Element newShow = document.createElement("show");
//                    newShow.setAttribute("object", "true");
//                    newShow.setAttribute("label", "true");
//                    newShow.setAttribute("ev", "4");
//                    newElement.appendChild(newShow);
//
//                    //objColor
//                    Element objColor = document.createElement("objColor");
//                    objColor.setAttribute("r", "21");
//                    objColor.setAttribute("g", "101");
//                    objColor.setAttribute("b", "192");
//                    objColor.setAttribute("alpha", "0");
//                    newElement.appendChild(objColor);
//                    //layer
//                    Element layer = document.createElement("layer");
//                    layer.setAttribute("val", "0");
//                    newElement.appendChild(layer);
//                    //labelMode
//                    Element labelMode = document.createElement("labelMode");
//                    labelMode.setAttribute("val", "0");
//                    newElement.appendChild(labelMode);
//
//                    //animation
//                    Element animation = document.createElement("animation");
//                    animation.setAttribute("step", "0.1");
//                    animation.setAttribute("speed", "1");
//                    animation.setAttribute("type", "1");
//                    animation.setAttribute("playing", "false");
//                    newElement.appendChild(animation);
//                    //pointSize
//                    Element pointSize = document.createElement("pointSize");
//                    pointSize.setAttribute("val", "5");
//                    newElement.appendChild(pointSize);
//                    //pointStyle
//                    Element pointStyle = document.createElement("pointStyle");
//                    pointStyle.setAttribute("val", "0");
//                    newElement.appendChild(pointStyle);
//                    //coords tag
//                    Element newCoords = document.createElement("coords");
//                    newCoords.setAttribute("x", p.xCords.get(j).toString());
//                    newCoords.setAttribute("y", String.valueOf(720 - p.yCords.get(j)));
//                    newCoords.setAttribute("z", "1");
//                    newElement.appendChild(newCoords);
//                    //element tag end
//
//                    //append element tag
//                    (root.getElementsByTagName("construction").item(0)).appendChild(newElement);
//                    //System.out.println("Print polygon vertices "+ j);
//
//                }
//                //command
//                System.out.println("Print polygon " + i);
//                Element newCommand = document.createElement("command");
//                newCommand.setAttribute("name", "Polygon");
//
//                Element commandInput = document.createElement("input");
//                for (int j = 0; j < p.xCords.size(); j++) {
//                    //A j add different unique string
//                    commandInput.setAttribute("a" + j, "A" + p.xCords.get(j));
//                }
//                newCommand.appendChild(commandInput);
//
//                Element commandOutput = document.createElement("output");
//                commandOutput.setAttribute("a0", "poly" + i);
//                newCommand.appendChild(commandOutput);
//                //append command tag
//                (root.getElementsByTagName("construction").item(0)).appendChild(newCommand);
//            }
//            // Saving changes to a new XML file
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            DOMSource source = new DOMSource(document);
//            StreamResult result = new StreamResult(new File("./Geogebra/savedFile/geogebra.xml"));
//            transformer.transform(source, result);
//            System.out.println("XML file updated successfully.");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

    public ArrayList<MyPolygon> readPolygon(String fn) {
        ArrayList<MyPolygon> polygonList = new ArrayList<MyPolygon>();
        try {
            MyPolygon p = new MyPolygon();
            //added code XML
            //System.out.println("file name "+fn);
            File inputFile = new File(fn);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            // Accessing root element
            Element root = document.getDocumentElement();
            // Reading data from XML
            NodeList nodeList = root.getElementsByTagName("construction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList commandPol = ((Element) nodeList.item(i)).getElementsByTagName("command");
                for (int k = 0; k < commandPol.getLength(); k++) {
                    Element geometricCommand = ((Element) commandPol.item(k));
                    int lengthInputCommand = geometricCommand.getElementsByTagName("input").item(0).getAttributes().getLength();
                    if (geometricCommand.getAttribute("name").equals("Polygon")) {

                        for (int e = 0; e < lengthInputCommand; e++) {
                            //System.out.println("length input length "+lengthInputCommand);
                            String polyPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(e).getNodeValue();
                            NodeList elems = ((Element) nodeList.item(i)).getElementsByTagName("element");
                            // System.out.println("elemtnts length " + elems.getLength());

                            for (int j = 0; j < elems.getLength(); j++) {
                                //System.out.println("elems length "+elems.getLength());
                                Element polygonElement = ((Element) elems.item(j));
                                String label = polygonElement.getAttribute("label");
                                String pointElement = polygonElement.getAttribute("type");
                                // System.out.println("ploypoint and label  " + polyPoints + " " + label);

                                if (polyPoints.equals(label) && pointElement.equals("point")) {
                                    //System.out.println("count");
                                    String xCoordinate = polygonElement.getElementsByTagName("coords").item(0).getAttributes().item(0).getNodeValue();
                                    String yCoordinate = polygonElement.getElementsByTagName("coords").item(0).getAttributes().item(1).getNodeValue();
                                    p.addPoint((int) (Float.parseFloat(xCoordinate)), (720 - (int) (Float.parseFloat(yCoordinate))));
                                    String polyEndPointLabel = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(lengthInputCommand - 1).getNodeValue();

                                    if (polyEndPointLabel.equals(polyPoints)) {
                                        System.out.println("poly end label "+polyEndPointLabel+" "+polyPoints);
                                        //p.addPoint((int) (Float.parseFloat(xCoordinate)), (720 - (int) (Float.parseFloat(yCoordinate))));
                                        polygonList.add(p);
                                        p = new MyPolygon((int) (Float.parseFloat(xCoordinate)), (720 - (int) (Float.parseFloat(yCoordinate))));
                                        p.xCords.remove(0);
                                        p.yCords.remove(0);

                                        break;
                                    }
                                    break;
                                }

                            }
                        }
                    }
                }
            }
            //added code XML END
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("polygon list " + polygonList);
        return polygonList;
    }

    public ArrayList<MyLine> readLine(String fn) {
        ArrayList<MyLine> myLineList = new ArrayList<MyLine>();
        try {

            MyLine p = new MyLine();
            //added code XML
            //System.out.println("file name "+fn);
            File inputFile = new File(fn);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            // Accessing root element
            Element root = document.getDocumentElement();
            // Reading data from XML
            NodeList nodeList = root.getElementsByTagName("construction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList commandPol = ((Element) nodeList.item(i)).getElementsByTagName("command");
                for (int k = 0; k < commandPol.getLength(); k++) {
                    Element geometricCommand = ((Element) commandPol.item(k));
                    int lengthInputCommand = geometricCommand.getElementsByTagName("input").item(0).getAttributes().getLength();
                    if (geometricCommand.getAttribute("name").equals("Segment")) {
                        // System.out.println("segments found ");
                        int count = 0;
                        for (int e = 0; e < lengthInputCommand; e++) {
                            String segPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(e).getNodeValue();
                            NodeList elems = ((Element) nodeList.item(i)).getElementsByTagName("element");
                            //System.out.println("elemtnts length " + elems.getLength());

                            for (int j = 0; j < elems.getLength(); j++) {
                                Element polygonElement = ((Element) elems.item(j));
                                String label = polygonElement.getAttribute("label");
                                String pointElement = polygonElement.getAttribute("type");
                                if (segPoints.equals(label) && pointElement.equals("point")) {
                                    count++;
                                    String xCoordinate = polygonElement.getElementsByTagName("coords").item(0).getAttributes().item(0).getNodeValue();
                                    String yCoordinate = polygonElement.getElementsByTagName("coords").item(0).getAttributes().item(1).getNodeValue();
                                    MyLine line;
                                    if (myLineList.size() == 0) {
                                        line = new MyLine((int) (Float.parseFloat(xCoordinate)), 720 - (int) (Float.parseFloat(yCoordinate)));
                                        myLineList.add(line);

                                    } else {
                                        line = myLineList.get(myLineList.size() - 1);
                                        if (line.get_P2() != null) {
                                            line = new MyLine((int) (Float.parseFloat(xCoordinate)), 720 - (int) (Float.parseFloat(yCoordinate)));
                                            myLineList.add(line);
                                        } else {
                                            line.addPoint((int) (Float.parseFloat(xCoordinate)), 720 - (int) (Float.parseFloat(yCoordinate)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //added code XML END
        } catch (Exception e) {
            System.out.println(e);
        }

        //System.out.println("polygon list " + polygonList);
       // System.out.println("mula saag "+myLineList.get(0).get_P1().get_x());
        return myLineList;
    }

    public ArrayList<MyPoint> readPoint(String fn) {
        ArrayList<MyPoint> myPointList = new ArrayList<MyPoint>();
        try {
            MyPoint p = new MyPoint();
            //added code XML
            //System.out.println("file name "+fn);
            File inputFile = new File(fn);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            // Accessing root element
            Element root = document.getDocumentElement();
            // Reading data from XML
            NodeList nodeList = root.getElementsByTagName("construction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList elems = ((Element) nodeList.item(i)).getElementsByTagName("element");
                for (int j = 0; j < elems.getLength(); j++) {
                    Element polygonElement = ((Element) elems.item(j));
                    Node polygonElementNode = elems.item(j);
                    if (polygonElementNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (polygonElement.getAttribute("type").equals("point")) {
                            Element polygonElementN = ((Element) polygonElementNode);
                            String label = polygonElementN.getAttribute("label");
                            String xCoordinate = polygonElementN.getElementsByTagName("coords").item(0).getAttributes().item(0).getNodeValue();
                            String yCoordinate = polygonElementN.getElementsByTagName("coords").item(0).getAttributes().item(1).getNodeValue();
                            //p.addPoint((int) (Float.parseFloat(xCoordinate)), (720 - (int) (Float.parseFloat(yCoordinate))));
                            String foundValue = "";
                            NodeList commandPol = ((Element) nodeList.item(i)).getElementsByTagName("command");
                            for (int k = 0; k < commandPol.getLength(); k++) {
                                Element geometricCommand = ((Element) commandPol.item(k));
                                int lengthInputCommand = geometricCommand.getElementsByTagName("input").item(0).getAttributes().getLength();
                                for (int e = 0; e < lengthInputCommand; e++) {
                                    if (geometricCommand.getAttribute("name").equals("Polygon")) {
                                        String polyPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(e).getNodeValue();
                                        //System.out.println(polyPoints);
                                        if (polyPoints.contains(label)) {
                                            foundValue = "found";
                                        }
                                    } else if (geometricCommand.getAttribute("name").equals("Segment")) {
                                        String segPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(e).getNodeValue();
                                        //System.out.println(polyPoints);
                                        if (segPoints.contains(label)) {
                                            foundValue = "found";
                                        }

                                    }
                                }

                            }
                            if (!foundValue.equals("found")) {
                                myPointList.add(new MyPoint((int) (Float.parseFloat(xCoordinate)), 720 - (int) (Float.parseFloat(yCoordinate))));
                            }
                        }
                    }
                }
            }
            //added code XML END
        } catch (Exception e) {
            System.out.println(e);
        }

        //System.out.println("polygon list " + polygonList);
        return myPointList;
    }

    public static void main(String[] args) {
        FileIO f = new FileIO("test");
//        try {
//            // Reading XML file
//            File inputFile = new File("./Geogebra/geogebra.xml");
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(inputFile);
//
//            // Accessing root element
//            Element root = document.getDocumentElement();
//            // System.out.println("Rootname: " + root.toString());
//
//            // Reading data from XML
//            NodeList nodeList = root.getElementsByTagName("construction");
//            //double[][] intArray = new double[20][20];
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                NodeList elems = ((Element) nodeList.item(i)).getElementsByTagName("element");
//                for (int j = 0; j < elems.getLength(); j++) {
//                    Element polygonElement = ((Element) elems.item(j));
//                    Node polygonElementNode = elems.item(j);
//                    if (polygonElementNode.getNodeType() == Node.ELEMENT_NODE) {
//                        if (polygonElement.getAttribute("type").equals("point")) {
//                            Element polygonElementN = ((Element) polygonElementNode);
//                            String label = polygonElement.getAttribute("label");
//                            String xCoordinate = polygonElementN.getElementsByTagName("coords").item(0).getAttributes().item(0).getNodeValue();
//                            String yCoordinate = polygonElementN.getElementsByTagName("coords").item(0).getAttributes().item(1).getNodeValue();
//                            String polygonNameData = "";
//                            String polyType = "";
//
//                            NodeList commandPol = ((Element) nodeList.item(i)).getElementsByTagName("command");
//                            for (int k = 0; k < commandPol.getLength(); k++) {
//                                Element geometricCommand = ((Element) commandPol.item(k));
//                                int lengthOutputCommand = geometricCommand.getElementsByTagName("output").item(0).getAttributes().getLength();
//                                int lengthInputCommand = geometricCommand.getElementsByTagName("input").item(0).getAttributes().getLength();
//                                // int test = 0;
//                                for (int z = 0; z < lengthOutputCommand; z++) {
//                                    //String polygonOutputPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(z).getNodeValue();
//                                    String polyName = geometricCommand.getElementsByTagName("output").item(0).getAttributes().item(0).getNodeValue();
//                                    if (geometricCommand.getAttribute("name").equals("Polygon")) {
//                                        for (int e = 0; e < lengthInputCommand; e++) {
//                                            String polyPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(e).getNodeValue();
//                                            //System.out.println(polyPoints);
//                                            if (polyPoints.contains(label)) {
//                                                polyType = "Polygon";
//                                                polygonNameData = polyName;
//                                                break;
//                                            }
//                                        }
//                                    } else if (geometricCommand.getAttribute("name").equals("Segment")) {
//                                        //System.out.println("label data "+ label);
//                                        for (int e = 0; e < lengthInputCommand; e++) {
//                                            String polyPoints = geometricCommand.getElementsByTagName("input").item(0).getAttributes().item(e).getNodeValue();
//                                            String polyNameD = geometricCommand.getElementsByTagName("output").item(0).getAttributes().item(0).getNodeValue();
//                                            if (polyPoints.contains(label)) {
//                                                //System.out.println(label);
//                                                polyType = polyType.equals("Polygon") ? "Polygon" : "Segment";
//                                                polygonNameData = polygonNameData.isEmpty() ? polyNameD : polygonNameData;
//                                                break;
//                                            }
//                                            //System.out.println(polyPoints);
//                                        }
//                                    } else {
//
//                                        System.out.println("hawa");
//
//                                    }
//                                }
//
//                            }
//                            if (polyType.equals("") || polyType.isEmpty()) {
//                                polyType = "Point";
//                            }
//                            System.out.println(polyType + " " + polygonNameData + " " + label + " " + xCoordinate + " " + yCoordinate);
//                        }
//
//                    }
//
//                }
//
//            }
//
//
//            // System.out.println("paremnty node"  + nodeList.item(0).getNodeName());
//
//
//            // Writing to XML file
//            Element newPerson = document.createElement("person");
//            newPerson.setAttribute("name", "John");
//
//            Element newAge = document.createElement("age");
//            newAge.appendChild(document.createTextNode("25"));
//            newPerson.appendChild(newAge);
//
//            root.appendChild(newPerson);
//
//            // Saving changes to a new XML file
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            DOMSource source = new DOMSource(document);
//            StreamResult result = new StreamResult(new File("output.xml"));
//            transformer.transform(source, result);
//
//            System.out.println("XML file updated successfully.");
//
//        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | TransformerException e) {
//            e.printStackTrace();
//        }
        // f.write();
    }


}
