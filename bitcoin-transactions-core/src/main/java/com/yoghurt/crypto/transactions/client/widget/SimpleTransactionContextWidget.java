package com.yoghurt.crypto.transactions.client.widget;

import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.i18n.M;
import com.yoghurt.crypto.transactions.client.widget.ContextFieldSet.FieldContextFactory;
import com.yoghurt.crypto.transactions.shared.domain.Operation;
import com.yoghurt.crypto.transactions.shared.domain.TransactionPartType;
import com.yoghurt.crypto.transactions.shared.domain.VariableLengthInteger;
import com.yoghurt.crypto.transactions.shared.util.NumberParseUtil;
import com.yoghurt.crypto.transactions.shared.util.ScriptOperationUtil;

public class SimpleTransactionContextWidget implements FieldContextFactory<Entry<TransactionPartType, byte[]>> {
  @Override
  public Widget getContextWidget(final Entry<TransactionPartType, byte[]> value) {
    final Label label = new Label(getTextForPart(value));
    label.getElement().getStyle().setPadding(10, Unit.PX);
    return label;
  }

  private String getTextForPart(final Entry<TransactionPartType, byte[]> value) {
    switch (value.getKey()) {
    case INPUT_PREVIOUS_TRANSACTION_HASH:
      return M.messages().inputPreviousTransactionHash();
    case INPUT_OUTPOINT_INDEX:
      return M.messages().previousOutputIndex() + NumberParseUtil.parseUint32(value.getValue());
    case OUTPUT_SCRIPT_LENGTH:
      return M.messages().outputScriptLength() + new VariableLengthInteger(value.getValue()).getValue() + M.messages().unitByte();
    case INPUT_SCRIPT_LENGTH:
      return M.messages().inputScriptLength() + new VariableLengthInteger(value.getValue()).getValue() + M.messages().unitByte();
    case INPUT_SEQUENCE:
      return M.messages().inputSequenceNumber();
    case INPUT_SIZE:
      return M.messages().numberOfInputs() + new VariableLengthInteger(value.getValue()).getValue();
    case OUTPUT_SIZE:
      return M.messages().numberOfOutputs() + new VariableLengthInteger(value.getValue()).getValue();
    case OUTPUT_VALUE:
      return M.messages().outputValue() + NumberParseUtil.parseLong(value.getValue()) / 100000000d + " " + M.messages().btc();
    case SCRIPT_PUB_KEY_OP_CODE:
      final Operation pubKeySigOp = ScriptOperationUtil.getOperation(value.getValue()[0] & 0xFF);
      if (ScriptOperationUtil.isDataPushOperation(pubKeySigOp) && pubKeySigOp.getOpcode() <= 75) {
        return "ScriptPubKey " + M.messages().operation() + pubKeySigOp.name() + " (" + (value.getValue()[0] & 0xFF) + M.messages().unitByte() + ")";
      } else {
        return "ScriptPubKey " + M.messages().operation() + pubKeySigOp.name();
      }
    case SCRIPT_SIG_OP_CODE:
      final Operation scriptSigOp = ScriptOperationUtil.getOperation(value.getValue()[0] & 0xFF);
      if (ScriptOperationUtil.isDataPushOperation(scriptSigOp) && scriptSigOp.getOpcode() <= 75) {
        return "ScriptSig " + M.messages().operation() + scriptSigOp.name() + " (" + (value.getValue()[0] & 0xFF) + M.messages().unitByte() + ")";
      } else {
        return "ScriptSig " + M.messages().operation() + scriptSigOp.name();
      }
    case SCRIPT_PUB_KEY_PUSH_DATA:
      return "ScriptPubKey " + M.messages().data();
    case SCRIPT_SIG_PUSH_DATA_EXTRA:
      return "OP_PUSHDATA(x) " + M.messages().mount()+ " (" + (value.getValue()[0] & 0xFF) + M.messages().unitByte() + ")";
    case SCRIPT_PUB_KEY_PUSH_DATA_EXTRA:
      return "OP_PUSHDATA(x) " + M.messages().mount()+ " (" + (value.getValue()[0] & 0xFF) + M.messages().unitByte() + ")";
    case SCRIPT_SIG_PUSH_DATA:
      return "ScriptSig " + M.messages().data();
    case LOCK_TIME:
      return M.messages().lockTime();
    case VERSION:
      return M.messages().transactionVersion();
    case COINBASE_SCRIPT_SIG:
      return M.messages().coinbaseInputScript();
    case WITNESS_MARKER:
      return M.messages().witnessMarker();
    case WITNESS_FLAG:
      return M.messages().witnessFlag();
    case WITNESS_ITEM_LENGTH:
      return M.messages().witnessNumber() +  new VariableLengthInteger(value.getValue()).getValue();
    case WITNESS_PUSH_DATA_LENGTH:
      return M.messages().witnessSize() + new VariableLengthInteger(value.getValue()).getValue() + M.messages().unitByte();
    case WITNESS_PUSH_DATA:
      return M.messages().witnessData();
    case ERROR:
      return M.messages().txError();
    default:
      return M.messages().txDefault();
    }
  }
}
