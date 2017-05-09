package uk.ac.aber.dcs.blockmotion.Transformations;

import uk.ac.aber.dcs.blockmotion.Transform;
import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Created by chris on 08/05/17.
 */
public class SlideD extends Transform {

    public void transform(IFrame frame){

        int rowsCols = frame.getNumRows();
        String[] data = new String[rowsCols*rowsCols];


        //fill an array from the frames array in reverse row order
        int q = rowsCols;

        for (int i = 0; i < rowsCols-1; i++){


            for (int j = 0; j < rowsCols; j++){
                //Will need to do row by row

                data[q] = Character.toString(frame.getChar(i,j));
                q++;

                data[j] = Character.toString(frame.getChar(rowsCols-1,j));

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
