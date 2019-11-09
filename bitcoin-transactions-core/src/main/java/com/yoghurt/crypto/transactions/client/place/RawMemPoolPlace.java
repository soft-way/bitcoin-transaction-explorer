package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Prefix;

public class RawMemPoolPlace extends ApplicationPlace {
  private static final String PREFIX = "mempool";

  public enum RawMemPoolDataType {
    LAST("last");

    private final String token;

    private RawMemPoolDataType(final String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public static RawMemPoolDataType fromToken(final String token) {
      for (final RawMemPoolDataType type : values()) {
        if (type.getToken().equals(token)) {
          return type;
        }
      }

      return null;
    }
  }

  private final RawMemPoolDataType type;
  private final String payload;

  @Prefix(PREFIX)
  public static class Tokenizer extends DelimitedTokenizer<RawMemPoolPlace> {
    @Override
    protected RawMemPoolPlace createPlace(final String[] tokens) {
      final RawMemPoolDataType type = RawMemPoolDataType.fromToken(tokens[0]);

      return tokens.length == 1 ? new RawMemPoolPlace(type) : new RawMemPoolPlace(type, tokens[1]);
    }

    @Override
    protected String[] getTokens(final RawMemPoolPlace place) {
      return place.payload == null ? new String[] { place.getType().getToken() } : new String[] { place.getType().getToken(), place.getPayload() };
    }
  }

  public RawMemPoolPlace(final RawMemPoolDataType type) {
    this(type, null);
  }

  public RawMemPoolPlace(final RawMemPoolDataType type, final String payload) {
    this.type = type;
    this.payload = payload;
  }

  public RawMemPoolDataType getType() {
    return type;
  }

  public String getPayload() {
    return payload;
  }

}
