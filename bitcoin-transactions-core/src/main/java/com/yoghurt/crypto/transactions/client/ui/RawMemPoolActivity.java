package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.client.place.RawMemPoolPlace;

public class RawMemPoolActivity extends LookupActivity<BlockInformation, RawMemPoolPlace> implements RawMemPoolView.Presenter {
  private final RawMemPoolView view;

  @Inject
  public RawMemPoolActivity(final RawMemPoolView view, @Assisted final RawMemPoolPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);

    this.view = view;

    view.setPresenter(this);
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final BlockInformation blockInformation) {
    panel.setWidget(view);

    if(blockInformation == null) {
      return;
    }
    
    view.setBlock();
  }

    @Override
  protected void doLookup(final RawMemPoolPlace place, final AsyncCallback<BlockInformation> callback) {
    switch (place.getType()) {
    case LAST:
      service.getBlockInformationLast(callback);
      break;
    default:
      callback.onFailure(new IllegalStateException("No support lookup for type: " + place.getType().name()));
      return;
    }
  }

  @Override
  public void loadRawMemPoolTransactionList() {
    service.getRawMemPoolTransactionList(new AppAsyncCallback<ArrayList<String>>() {
      @Override
      public void onSuccess(final ArrayList<String> result) {
        view.setRawMemPoolTransactionList(result);
      }
    });
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
  }
}