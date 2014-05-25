
package test.expedia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aman
 */
public class GetWeatherInfo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject sendBack=new JSONObject();
        try {
            
            response.setContentType("application/json");
            String city=request.getParameter("zip");
            String url = "http://api.wunderground.com/api/ed044d75b91fb500/conditions/q/"+city+".json";
            PrintWriter out = response.getWriter();
            if(!city.matches("^[0-9]+$") || (city.length()!=5)){
                sendBack.put("error", "invalid zip code format");
                out.println(sendBack.toJSONString());
                return;
            }
            HttpClient  client =HttpClients.createDefault();
            HttpGet get=new HttpGet(url);
            HttpResponse res=client.execute(get);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
             
             
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONParser parser=new JSONParser();
            JSONObject obj=(JSONObject)parser.parse(builder.toString());
            JSONObject temp=null;
            Set<Map.Entry> e=obj.entrySet();
            for(Object o: e){
                obj=(JSONObject)parser.parse(((Map.Entry)o).getValue().toString());
                 Set<String> resObj=obj.keySet();
                if(((Map.Entry)o).getKey().equals("response")){
                    
                    if(resObj.contains("error")){
                        sendBack.put("error", "zipcode not found");
                        break;
                    }
                }
                else if(((Map.Entry)o).getKey().equals("current_observation")){
                    temp=obj;
                    obj=(JSONObject)parser.parse(((Map.Entry)o).getValue().toString());
                    Set<Map.Entry> observation=obj.entrySet();
                    for(Map.Entry it: observation){
                        if(((Map.Entry)it).getKey().equals("display_location")){
                            obj=(JSONObject)parser.parse(((Map.Entry)it).getValue().toString());
                            if(obj.containsKey("city"))
                                sendBack.put("City", (String)obj.get((Object)"city"));
                            if(obj.containsKey("state_name"))
                                sendBack.put("State Name",(String)obj.get((Object)"state_name"));
                        }
                        if(((Map.Entry)it).getKey().equals("temp_f"))
                            sendBack.put("Temperature",it.getValue());
                            
                    }
                    
                    }
                }
            
            out.println(sendBack.toJSONString());
            
        } catch (Exception ex) {
            sendBack.put("Exception", "Some exception");
            response.getWriter().println(sendBack.toJSONString());
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
