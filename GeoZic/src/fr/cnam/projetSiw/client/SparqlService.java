package fr.cnam.projetSiw.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("sparql")
public interface SparqlService extends RemoteService{
	String getSparqlDatas();

}
