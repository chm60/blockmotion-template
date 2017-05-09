package uk.ac.aber.dcs.blockmotion;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by chris on 27/04/17.
 */
public class Frame implements IFrame {
    String[] frameContents;
    public int frameRowsCols;

    @Override
    public void insertLines(String[] lines) {

        int rowsPos = lines.length-1;
        frameRowsCols = Integer.parseInt(lines[rowsPos]);

        frameContents = Arrays.copyOfRange(lines, 0,(rowsPos+1));


    }

    @Override
    public int getNumRows() {


        return frameRowsCols;
    }

    @Override
    public void tofile(PrintWriter outfile) {

        for(int i = 0; i < frameRowsCols; i++){

            String str = new String();
            for(int x = 0; x < frameRowsCols; x++){
                int num = i*frameRowsCols;

                str += frameContents[num+x];


            }

            outfile.print(str + "\r\n");

        }

    }

    @Override
    public char getChar(int i, int j) {

        this.getNumRows();
        int x;
        if(i < 1){
            x = j;
        }else{x = (i*frameRowsCols)+j;
        }

        char ch = frameContents[x].charAt(0);
        return ch;
    }

    @Override
    public void setChar(int i, int j, char ch) {
        int x;
        if(i < 1){
            x = j;
        }else{x = (i*frameRowsCols)+j;
        }
        frameContents[x] = Character.toString(ch);


    }

    @Override
    public IFrame copy() {
        return null;
    }

    @Override
    public void replace(IFrame f) {

        this.getNumRows();

      //  String[] flippedData = new String[frameRowsCols*frameRowsCols];
        int q = 0;
        for (int i = 0; i < frameRowsCols; i ++){
            for (int j = 0; j < frameRowsCols; j++){

               // flippedData[q] = Character.toString(f.getChar(i,j));
                this.setChar(i,j,f.getChar(i,j));

                q++;
            }

        }



    }
}
