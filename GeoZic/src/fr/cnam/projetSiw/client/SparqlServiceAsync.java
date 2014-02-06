package fr.cnam.projetSiw.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SparqlServiceAsync {

	void getSparqlDatas(AsyncCallback<String> callback);

}
