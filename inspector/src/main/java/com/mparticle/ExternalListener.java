package com.mparticle;

import android.support.annotation.Nullable;

import com.mparticle.identity.MParticleUser;
import com.mparticle.internal.InternalSession;
import com.mparticle.inspector.TrackableObject;

import org.json.JSONObject;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExternalListener {
    void onApiMethodCalled(Integer id, String name, List<TrackableObject> trackableObjects, Boolean isExternal);

    void onMessageCreated(String table, Long rowId, Integer messageId, JSONObject message);
    void onMessageUploaded(Integer messageId, Integer networkRequestId);

    void onNetworkRequestStarted(Integer id, String type, String url, JSONObject body);
    void onNetworkRequestFinished(Integer id, String url, JSONObject response, int responseCode);

    void kitFound(int kitId, String kitName);
    void kitConfigReceived(int kitId, String kitName, String configuration);
    void kitExcluded(int kitId, String kitName, String reason);
    void kitStarted(int kitId, String kitName);

    void onKitMethodCalled(Integer id, Integer kitId, String name, Boolean used, List<TrackableObject> objects);

    void onSessionUpdated(@Nullable InternalSession session);

    void onUserIdentified(@Nullable MParticleUser user);


    void updateComposites(Map<Integer, ? extends Set<? extends LinkedHashSet<Integer>>> parentsChildren, Map<Integer, ? extends Set<? extends LinkedHashSet<Integer>>> childrensParents);
}
