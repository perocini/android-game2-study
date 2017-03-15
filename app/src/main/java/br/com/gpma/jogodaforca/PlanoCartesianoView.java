package br.com.gpma.jogodaforca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PlanoCartesianoView extends View {

    //Validar qual o menor lado do display (width ou height)
    private int menorLadoDisplay;
    private float unidade;

    public PlanoCartesianoView(Context context) {
        super(context);
    }

    public PlanoCartesianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getMenorLadoDisplay() {
        return menorLadoDisplay;
    }
    public void setMenorLadoDisplay(int menorLadoDisplay) {
        this.menorLadoDisplay = menorLadoDisplay;
    }
    public float getUnidade() {
        return unidade;
    }
    public void setUnidade(int unidade) {
        this.unidade = unidade;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(getHeight() > getWidth())
            setMenorLadoDisplay(getWidth());
        else
            setMenorLadoDisplay(getHeight());

        setUnidade(getMenorLadoDisplay() / 10);

        //drawPlanoCartesiano(canvas);
    }

    public void drawPlanoCartesiano(Canvas canvas) {
        Path path = new Path();

        float max = toPixel(10);
        for(int n = 0; n <=10; ++n) {
            //Linhas verticais
            path.moveTo(toPixel(n), 1);
            path.lineTo(toPixel(n), max);
            //Linhas horizontais
            path.moveTo(1, toPixel(n));
            path.lineTo(max, toPixel(n));
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        canvas.drawPath(path, paint);
    }

    protected float toPixel(float vezes) {
        return vezes * getUnidade();
    }
}
