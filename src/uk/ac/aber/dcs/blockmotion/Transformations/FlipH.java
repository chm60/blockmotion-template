package uk.ac.aber.dcs.blockmotion.Transformations;

import uk.ac.aber.dcs.blockmotion.Frame;
import uk.ac.aber.dcs.blockmotion.Transform;
import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Created by chris on 27/04/17.
 */
public class FlipH extends Transform{



        public void transform(IFrame frame){
        int rowsCols = frame.getNumRows();
        String[] data = new String[rowsCols*rowsCols];


        //fill an array from the frames array in reverse column order
        int q = 0;

        for (int i = rowsCols; i > 0; i--){
            for (int j = rowsCols; j > 0; j--){

                    data[q] = Character.toString(frame.getChar(i-1,j-1));


                q++;
            }

        }

        int z = 0;
            //fills frame with new flipped data
            for(int i=0; i<rowsCols;i++){

                for(int j=0;j<rowsCols;j++){

                frame.setChar(i,j,data[z].charAt(0));
                z++;
            }

        }

    }




}
