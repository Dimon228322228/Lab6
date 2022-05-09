package reader;

import java.io.*;

public class ExchangeController {

    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    private PrintStream err = System.err;

    public void writeMassage(String str) throws IOException {
        out.flush();
        out.write(str);
    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public void writeErr(String str){
        err.println(str);
    }

    public ExchangeController replaceOut(BufferedWriter writer){
        out = writer;
        return this;
    }

    public ExchangeController replaceIn(BufferedReader reader){
        in = reader;
        return this;
    }

    public ExchangeController replaceErr(PrintStream printStream){
        err = printStream;
        return this;
    }

}
