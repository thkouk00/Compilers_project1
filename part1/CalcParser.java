import java.io.InputStream;
import java.io.IOException;


class CalcParser{
    private int lookaheadToken;
    private InputStream in;

    public CalcParser(InputStream in) throws IOException{
        this.in = in;
        lookaheadToken = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
        if (lookaheadToken != symbol)
            throw new ParseError();
        lookaheadToken  = in.read();  //read next char
    }

    private int evalDigit(int digit){
        return digit - '0';
    }

    private int Expr() throws IOException, ParseError {
        int res = Term();
        int res2 = Expr1(res);
        
        return res2;
    }

    private int Expr1(int cond) throws IOException, ParseError {
        if (lookaheadToken == '\n' || lookaheadToken == -1)
            return cond;

        if (lookaheadToken == '^'){
            consume('^');
            int res1 = Term();
            int res2 = Expr1(res1);
            
            return (cond ^ res2);
        }

        return cond;
    }

    private int Term() throws IOException, ParseError {
        int res1 = Factor();
        int res2 = Term1(res1);
        
        return res2; 
    }

    private int Term1(int cond) throws IOException, ParseError {
        if(lookaheadToken == '\n' || lookaheadToken == -1)
            return cond;

        if (lookaheadToken == '&'){
            consume('&');
            int res1 = Factor();
            int res2 = Term1(res1);
            
            return cond & res2;
        }
        return cond;
    }

    private int Factor() throws IOException, ParseError{
        int res = 0;
        if (lookaheadToken >= '0' && lookaheadToken <= '9' ){
            res = evalDigit(lookaheadToken);
            consume(lookaheadToken);
        }
        else if (lookaheadToken != '(') {
            throw new ParseError();
        }
        else{
            consume('(');
            res = Expr();                 
            consume(')');
        }
        return res;

    }

    private void parse() throws IOException, ParseError {
        int res = Expr();
        if (lookaheadToken != '\n' && lookaheadToken != -1)
            throw new ParseError();

        System.out.println("Result is " + " " + res);
    }

    public static void main(String[] args) {
        try {
            CalcParser parser = new CalcParser(System.in);
            parser.parse();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
        catch (ParseError err){
            System.err.println(err.getMessage());
        }
    }
}
