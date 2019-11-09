package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;
import com.google.gwt.user.client.ui.IsWidget;

public interface RawMemPoolView extends IsWidget {
  public interface Presenter {
    void loadRawMemPoolTransactionList();
  }

  void setBlock();

  void setRawMemPoolTransactionList(ArrayList<String> transactions);

  void setPresenter(Presenter presenter);
}
