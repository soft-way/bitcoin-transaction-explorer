package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.yoghurt.crypto.transactions.client.widget.TransactionViewer;
import com.yoghurt.crypto.transactions.client.di.BitcoinPlaceRouter;

import gwt.material.design.client.ui.MaterialButton;

@Singleton
public class RawMemPoolViewImpl extends Composite implements RawMemPoolView {
  interface RawMemPoolViewImplUiBinder extends UiBinder<Widget, RawMemPoolViewImpl> {}

  private static final RawMemPoolViewImplUiBinder UI_BINDER = GWT.create(RawMemPoolViewImplUiBinder.class);

  @UiField
  MaterialButton loadRawMemPoolTransactionsButton;
  
  @UiField
  FlowPanel transactionPanel;

  private final BitcoinPlaceRouter router;
  private Presenter presenter;

  @Inject
  public RawMemPoolViewImpl(final BitcoinPlaceRouter router) {
    this.router = router;

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void setBlock() {
    try {
      transactionPanel.clear();
      loadRawMemPoolTransactionsButton.setVisible(true);
    } catch (final Throwable e) {
      e.printStackTrace();
    }  
  }

  @UiHandler("loadRawMemPoolTransactionsButton")
  public void onRawMemPoolTransactionListClick(final ClickEvent e) {
    presenter.loadRawMemPoolTransactionList();
    loadRawMemPoolTransactionsButton.setVisible(false);
  }

  @Override
  public void setRawMemPoolTransactionList(final ArrayList<String> transactions) {
    for (final String txid : transactions) {
      final TransactionViewer hashViewer = new TransactionViewer(router, false, false);
      transactionPanel.add(hashViewer);

      hashViewer.setValue(Hex.decode(txid));
    }
  }

}
