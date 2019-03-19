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
//        res = evalDigit(res);
        int res2 = Expr1(res);
        System.out.println("Return "+res2);
        return res2;
    }

    private int Expr1(int cond) throws IOException, ParseError {
        if (lookaheadToken == '\n' || lookaheadToken == -1)
            return cond;

        if (lookaheadToken == '^'){
            System.out.println("Expr1 Consume ^");
            consume('^');
            int res1 = Term();
            int res2 = Expr1(res1);
            System.out.println("Expr1 Cond "+ " "+ cond);
            System.out.println("Expr1 Einai ** "+ " "+ res2);
            System.out.println("Expr1 Episis " + (evalDigit(cond) ^ evalDigit(res2)));
//            return (evalDigit(cond) ^ evalDigit(res2));
            return (cond ^ res2);
        }
        return cond;
    }

    private int Term() throws IOException, ParseError {
        int res1 = Factor();
        System.out.println("Term Factor res "+ res1);
        int res2 = Term1(res1);
        System.out.println("Term Einai res1 = "+ " " + res1+ " kai res2 = "+res2);
        return res2; // lathos to ^
    }

    private int Term1(int cond) throws IOException, ParseError {
        if(lookaheadToken == '\n' || lookaheadToken == -1)
            return cond;

        if (lookaheadToken == '&'){
            consume('&');
            int res1 = Factor();
            System.out.println("Term1 res1 "+ res1);
            int res2 = Term1(res1);
            System.out.println("Term1 res2 "+ res2);
            System.out.println(cond & res2);
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
            res = Expr();                 // thelei metatropi
            consume(')');
        }
        return res;

    }

    private void parse() throws IOException, ParseError {
        int res = Expr();
        if (lookaheadToken != '\n' && lookaheadToken != -1)
            throw new ParseError();
//        System.out.println("Result is " + " " + evalDigit(res));
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
