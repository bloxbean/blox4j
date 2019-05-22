package graphql.tests;

import com.bloxbean.blox4j.impl.aion.pool.AionConnection;
import com.bloxbean.blox4j.impl.aion.service.TxnServiceImpl;
import com.bloxbean.blox4j.impl.aion.service.dao.AionBlockchainAccessor;
import com.bloxbean.blox4j.model.TxDetails;
import com.bloxbean.blox4j.pool.ConnectionHelper;
import com.bloxbean.blox4j.service.BlockService;
import org.aion.api.IAionAPI;
import org.aion.api.IChain;
import org.aion.api.type.ApiMsg;
import org.aion.api.type.ApiMsg.cast;
import org.aion.api.type.Transaction;
import org.aion.api.type.Transaction.TransactionBuilder;

import org.aion.base.type.AionAddress;
import org.aion.base.type.Hash256;
import org.aion.base.util.ByteArrayWrapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TxnApiTest {

  @Mock
  private IAionAPI api;

  @Mock
  private IChain iChain;

  @Mock
  private ConnectionHelper connectionHelper;

  @Mock
  BlockService blockService;

  @InjectMocks
  TxnServiceImpl txnService;

  @Test
  @Ignore
  public void testGetTransactionByHash() {

    AionBlockchainAccessor accessor = new AionBlockchainAccessor(connectionHelper);
    txnService = new TxnServiceImpl(null, accessor);

    TransactionBuilder builder = new TransactionBuilder();
    builder.from(AionAddress.wrap("a0c0cc973a306d31320fe72cad62afaa799d076bbd492ca4d5d5d941adfa12a9"));
    builder.to(AionAddress.wrap("a0325401995789edf2287def289d3c6d93c9e0771b47209104cfa1b722f0c9cc"));
    builder.value(BigInteger.valueOf(4000));
    builder.blockHash(Hash256.ZERO_HASH());
    builder.blockNumber(1);
    builder.txHash(Hash256.ZERO_HASH());
    builder.timeStamp(System.currentTimeMillis());
    builder.data(ByteArrayWrapper.wrap("sample data".getBytes()));
    builder.nonce(BigInteger.ONE);
    builder.nrgConsumed(21000);
    builder.nrgPrice(100000000);

    Transaction txn = builder.createTransaction();

    when(connectionHelper.getConnection()).thenReturn(new AionConnection(api, new ApiMsg()));
    when(api.getChain()).thenReturn(iChain);
    when(api.getChain().getTransactionByHash(any())).thenReturn(new ApiMsg().set(txn, cast.OTHERS));

    TxDetails txDetails = txnService.getTransaction(Hash256.ZERO_HASH().toString());

    assertThat(txDetails.getFrom().toString(), is(equalTo("a0c0cc973a306d31320fe72cad62afaa799d076bbd492ca4d5d5d941adfa12a9")));
    assertThat(txDetails.getTo().toString(), is(equalTo("a0325401995789edf2287def289d3c6d93c9e0771b47209104cfa1b722f0c9cc")));


  }
}
