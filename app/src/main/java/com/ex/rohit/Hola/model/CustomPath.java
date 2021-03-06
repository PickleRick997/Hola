package com.ex.rohit.Hola.model;

import android.graphics.Path;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Flash on 2018/4/17.
 */

public class CustomPath extends Path implements Serializable {

    private static final long serialVersionUID = -5974912367682897467L;

    private ArrayList<PathAction> actions = new ArrayList<CustomPath.PathAction>();

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
            in.defaultReadObject();
            drawThisPath();
    }

    @Override
    public void moveTo(float x, float y) {
        actions.add(new ActionMove(x, y));  //向actions队列中加一个移动画笔的动作
        super.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y){
        actions.add(new ActionLine(x, y));
        super.lineTo(x, y);
    }
    public ArrayList<PathAction> getActions(){
        return this.actions;
    }

    public void setActions(ArrayList<PathAction> actions) {
        this.actions = actions;
    }

    private void drawThisPath(){
        for(PathAction p : actions){
            if(p.getType().equals(PathAction.PathActionType.MOVE_TO)){
                super.moveTo(p.getX(), p.getY());
            } else if(p.getType().equals(PathAction.PathActionType.LINE_TO)){
                super.lineTo(p.getX(), p.getY());
            }
        }
    }

    public interface PathAction {
        public enum PathActionType {LINE_TO,MOVE_TO};
        public PathActionType getType();
        public float getX();
        public float getY();
    }

    public class ActionMove implements PathAction, Serializable{
        private static final long serialVersionUID = -7198142191254133295L;

        private float x,y;

        public ActionMove(float x, float y){
            this.x = x;
            this.y = y;
        }

        @Override
        public PathActionType getType() {
            return PathActionType.MOVE_TO;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

    }

    public class ActionLine implements PathAction, Serializable {
        private static final long serialVersionUID = 8307137961494172589L;

        private float x,y;

        public ActionLine(float x, float y){
            this.x = x;
            this.y = y;
        }

        @Override
        public PathActionType getType() {
            return PathActionType.LINE_TO;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

    }

    @Override
    public void reset() {
        super.reset();
        actions.clear();
    }
}
