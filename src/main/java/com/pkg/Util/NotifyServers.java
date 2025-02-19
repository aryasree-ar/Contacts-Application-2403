package com.pkg.Util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.pkg.Dao.ServerDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.Servers;

public class NotifyServers {
    
	public void notifyAllServers(String sessionId) throws URISyntaxException, IOException, InterruptedException, InvalidInputException, DBException {

    	List<Servers> availableServers = ServerDao.getAvailableServers();
    	HttpClient client = HttpClient.newHttpClient();
    	for(Servers server: availableServers) {
    		String encodedSessionID = URLEncoder.encode(sessionId, "UTF-8");
    		String serverNotifyUrl = "http://"+server.getServerIp()+":"+server.getPortNumber()+"/ContactsApp/notify?sessionId="+encodedSessionID;
    		HttpRequest httpRequestObject = HttpRequest.newBuilder().uri(new URI(serverNotifyUrl)).GET().build();
    		try {
                client.send(httpRequestObject, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }


}
