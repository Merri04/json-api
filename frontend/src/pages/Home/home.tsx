import { useState, useEffect } from "react";
import QRCode from "react-qr-code";

const QRCodeComponent: React.FC = () => {
    const [qrUrl, setQrUrl] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch('/api/bevis/send')
            .then((response) => response.json())
            .then((data: { url?: string }) => {
                if (data.url) {
                    setQrUrl(data.url);
                } else {
                    setError("Ingen URL mottatt fra API");
                }
            })
            .catch(() => setError("Feil ved henting av QR-kode"));
    }, []);

    return (
        <div className="flex flex-col items-center justify-center h-screen text-center">
            <h1 className="text-2xl font-bold">Scann for å få ditt digitale vitnemål</h1>
            {error && <p className="text-red-500 mt-2">{error}</p>}
            {qrUrl ? (
                <div className="mt-5">
                    <QRCode value={qrUrl} size={300} />
                </div>
            ) : (
                <p className="mt-5">FEIL</p>
            )}
        </div>
    );
};

export default QRCodeComponent;