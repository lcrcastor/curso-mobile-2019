package org.bouncycastle.crypto.tls;

import java.util.Hashtable;
import java.util.Vector;

public abstract class AbstractTlsClient extends AbstractTlsPeer implements TlsClient {
    protected TlsCipherFactory cipherFactory;
    protected short[] clientECPointFormats;
    protected TlsClientContext context;
    protected int[] namedCurves;
    protected int selectedCipherSuite;
    protected short selectedCompressionMethod;
    protected short[] serverECPointFormats;
    protected Vector supportedSignatureAlgorithms;

    public AbstractTlsClient() {
        this(new DefaultTlsCipherFactory());
    }

    public AbstractTlsClient(TlsCipherFactory tlsCipherFactory) {
        this.cipherFactory = tlsCipherFactory;
    }

    public Hashtable getClientExtensions() {
        Hashtable hashtable = null;
        if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(this.context.getClientVersion())) {
            short[] sArr = {6, 5, 4, 3, 2};
            short[] sArr2 = {1};
            this.supportedSignatureAlgorithms = new Vector();
            for (int i = 0; i < sArr.length; i++) {
                for (short signatureAndHashAlgorithm : sArr2) {
                    this.supportedSignatureAlgorithms.addElement(new SignatureAndHashAlgorithm(sArr[i], signatureAndHashAlgorithm));
                }
            }
            this.supportedSignatureAlgorithms.addElement(new SignatureAndHashAlgorithm(2, 2));
            hashtable = TlsExtensionsUtils.ensureExtensionsInitialised(null);
            TlsUtils.addSignatureAlgorithmsExtension(hashtable, this.supportedSignatureAlgorithms);
        }
        if (!TlsECCUtils.containsECCCipherSuites(getCipherSuites())) {
            return hashtable;
        }
        this.namedCurves = new int[]{23, 24};
        this.clientECPointFormats = new short[]{0, 1, 2};
        Hashtable ensureExtensionsInitialised = TlsExtensionsUtils.ensureExtensionsInitialised(hashtable);
        TlsECCUtils.addSupportedEllipticCurvesExtension(ensureExtensionsInitialised, this.namedCurves);
        TlsECCUtils.addSupportedPointFormatsExtension(ensureExtensionsInitialised, this.clientECPointFormats);
        return ensureExtensionsInitialised;
    }

    public ProtocolVersion getClientHelloRecordLayerVersion() {
        return getClientVersion();
    }

    public Vector getClientSupplementalData() {
        return null;
    }

    public ProtocolVersion getClientVersion() {
        return ProtocolVersion.TLSv12;
    }

    public TlsCompression getCompression() {
        if (this.selectedCompressionMethod == 0) {
            return new TlsNullCompression();
        }
        throw new TlsFatalAlert(80);
    }

    public short[] getCompressionMethods() {
        return new short[]{0};
    }

    public ProtocolVersion getMinimumVersion() {
        return ProtocolVersion.TLSv10;
    }

    public TlsSession getSessionToResume() {
        return null;
    }

    public void init(TlsClientContext tlsClientContext) {
        this.context = tlsClientContext;
    }

    public void notifyNewSessionTicket(NewSessionTicket newSessionTicket) {
    }

    public void notifySelectedCipherSuite(int i) {
        this.selectedCipherSuite = i;
    }

    public void notifySelectedCompressionMethod(short s) {
        this.selectedCompressionMethod = s;
    }

    public void notifyServerVersion(ProtocolVersion protocolVersion) {
        if (!getMinimumVersion().isEqualOrEarlierVersionOf(protocolVersion)) {
            throw new TlsFatalAlert(70);
        }
    }

    public void notifySessionID(byte[] bArr) {
    }

    public void processServerExtensions(Hashtable hashtable) {
        if (hashtable == null) {
            return;
        }
        if (hashtable.containsKey(TlsUtils.EXT_signature_algorithms)) {
            throw new TlsFatalAlert(47);
        } else if (TlsECCUtils.getSupportedEllipticCurvesExtension(hashtable) != null) {
            throw new TlsFatalAlert(47);
        } else {
            this.serverECPointFormats = TlsECCUtils.getSupportedPointFormatsExtension(hashtable);
            if (this.serverECPointFormats != null && !TlsECCUtils.isECCCipherSuite(this.selectedCipherSuite)) {
                throw new TlsFatalAlert(47);
            }
        }
    }

    public void processServerSupplementalData(Vector vector) {
        if (vector != null) {
            throw new TlsFatalAlert(10);
        }
    }
}
