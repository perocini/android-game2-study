package br.com.gpma.jogodaforca;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by Gustavo on 06/05/2016.
 */
public class ForcaView extends PlanoCartesianoView {

    private enum Membro {braco, perna}
    private enum Lado {direito, esquerdo}

    //responsável por desenhar as figuras geométricas
    private Path pathForca;
    private Paint paintForca;
    private ForcaController forcaController;

    public ForcaView(Context context) {
        super(context);
    }
    public ForcaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPathForca(new Path());
    }
    public ForcaController getForcaController() {
        return forcaController;
    }
    public void setForcaController(ForcaController forcaController) {
        this.forcaController = forcaController;
    }
    public Path getPathForca() {
        return pathForca;
    }
    public void setPathForca(Path pathForca) {
        this.pathForca = pathForca;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        setPathForca(new Path());
        DesenhaForca();
        DesenhaBoneco();
        DesenhaLetrasCorretas(canvas);
        DesenhaLetrasErradas(canvas);
        DesenhaTraco();
        canvas.drawPath(getPathForca(), getPaintForca());
    }

    private void DesenhaBoneco() {

        if(getForcaController()==null) return;
        switch (getForcaController().getQtdeErros()) {
            case 6:
                DesenhaMembro(Membro.perna, Lado.direito);
            case 5:
                DesenhaMembro(Membro.perna, Lado.esquerdo);
            case 4:
                DesenhaMembro(Membro.braco, Lado.direito);
            case 3:
                DesenhaMembro(Membro.braco, Lado.esquerdo);
            case 2:
                DesenhaCorpo();
            case 1:
                DesenhaCabeca();
        }
    }

    private void DesenhaForca() {
        getPathForca().moveTo(toPixel(1), toPixel(8));
        getPathForca().lineTo(toPixel(3), toPixel(8));
        getPathForca().moveTo(toPixel(2), toPixel(8));
        getPathForca().lineTo(toPixel(2), toPixel(1));
        getPathForca().moveTo(toPixel(2), toPixel(1));
        getPathForca().lineTo(toPixel(5), toPixel(1));
        getPathForca().moveTo(toPixel(5), toPixel(1));
        getPathForca().lineTo(toPixel(5), toPixel(2));
    }

    private void DesenhaCabeca() {
        getPathForca().addCircle(toPixel(5),toPixel(3),toPixel(1), Path.Direction.CW);
    }

    private void DesenhaCorpo() {
        getPathForca().moveTo(toPixel(5), toPixel(4));
        getPathForca().lineTo(toPixel(5), toPixel(6));
    }

    private void DesenhaMembro(Membro membro, Lado lado) {
        final int posDoCorpo = 5;
        final int alturaDoBraco = 4;
        final int alturaDaPerna = 6;
        int alturaFinal;

        if(membro == Membro.braco) {
            getPathForca().moveTo(toPixel(posDoCorpo), toPixel(alturaDoBraco));
            alturaFinal = alturaDoBraco + 1;
        } else {
            getPathForca().moveTo(toPixel(posDoCorpo), toPixel(alturaDaPerna));
            alturaFinal = alturaDaPerna + 1;
        }
        if(lado == Lado.direito)
            getPathForca().lineTo(toPixel(posDoCorpo + 1), toPixel(alturaFinal));
        else
            getPathForca().lineTo(toPixel(posDoCorpo - 1), toPixel(alturaFinal));
    }

    private void DesenhaLetrasCorretas(Canvas canvas) {
        float eixoX = toPixel(1);
        getPathForca().moveTo(eixoX + 10, toPixel(10));
        eixoX += 35;

        if(getForcaController()==null) return;

        for(int i = 0; i <= getForcaController().getPalavraAteAgora().length()-1; i++) {
            char c = getForcaController().getPalavraAteAgora().charAt(i);
            canvas.drawText(c+"", eixoX+((toPixel(1)+10) * i), toPixel(10)-15, getPaintTraco());
        }
    }

    private void DesenhaLetrasErradas(Canvas canvas) {
        float eixoX = toPixel(8);
        if(getForcaController()==null) return;
        canvas.drawText("Letras", toPixel(7.25f), toPixel(1), getPaintTraco());
        canvas.drawText("Erradas", toPixel(7), toPixel(1.75f), getPaintTraco());
        for(int i = 0; i <= getForcaController().getLetrasErradas().length()-1; i++) {
            char c = getForcaController().getLetrasErradas().charAt(i);
            canvas.drawText(c+"", eixoX, toPixel(i + 3), getPaintTraco());
        }
    }

    private void DesenhaTraco() {
        float eixoX = toPixel(1);
        getPathForca().moveTo(eixoX+10, toPixel(10));
        if(forcaController==null) return;

        for(int i=0; i<=getForcaController().getPalavraAteAgora().length()-1; i++) {
            getPathForca().rMoveTo(10, 0);
            getPathForca().rLineTo(toPixel(1), 0);
        }
    }

    private Paint getPaintTraco() {
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(2);
        p.setTextSize( 30 );
        return p;
    }

    public Paint getPaintForca() {
        paintForca = new Paint();
        paintForca.setColor(Color.BLACK);
        paintForca.setStyle(Paint.Style.STROKE);
        paintForca.setStrokeWidth(8);
        return paintForca;
    }
}
