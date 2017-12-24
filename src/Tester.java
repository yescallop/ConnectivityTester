import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetAddress;

public class Tester {
    private final HttpUriRequest httpRequest;
    private final CloseableHttpClient httpClient;
    private final InetAddress[] addrs;
    private final int times;

    public Tester(String url, InetAddress[] addrs, int times) {
        HttpGet httpRequest = new HttpGet(url);
        httpRequest.setConfig(RequestConfig.custom()
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .build());
        this.httpRequest = httpRequest;
        SSLContext ctx;
        try {
            ctx = new SSLContextBuilder()
                    .loadTrustMaterial(null, (TrustStrategy) (x, s) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(ctx, new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
        this.httpClient = HttpClients.custom()
                .setSSLSocketFactory(factory)
                .disableAutomaticRetries()
                .build();
        this.addrs = addrs;
        this.times = times;
    }

    private void _test(int i) {
        HttpHost target = new HttpHost(addrs[i], -1, httpRequest.getURI().getScheme());
        for (int n = 0; n < times; n++) {
            try {
                long start = System.currentTimeMillis();
                EntityUtils.consume(httpClient.execute(target, httpRequest).getEntity());
                System.out.print(" " + (System.currentTimeMillis() - start));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print(" X");
            }
        }
    }

    public void test() {
        for (int i = 0; i < addrs.length; i++) {
            System.out.print(addrs[i].getHostAddress());
            _test(i);
            System.out.println();
        }
    }
}
