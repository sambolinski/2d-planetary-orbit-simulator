package uk.ac.city.acwf566.handler;


import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import uk.ac.city.acwf566.gui.PhysicsPanel;
import uk.ac.city.acwf566.gui.View;
import uk.ac.city.acwf566.objects.Body;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sam
 */
public class Main {
    public static void main(String[] args){
       // Body Sun = new Body();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        PhysicsHandler model = new PhysicsHandler();
        PhysicsPanel physicsPanel = new PhysicsPanel(model);
        View view = new View(physicsPanel);
        view.setVisible(true);
        SimulationController controller = new SimulationController(view, model);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                controller.initWorld();
                Timer timer = new Timer(1, new ActionListener(){
                    @Override
                    public void actionPerformed( ActionEvent e ){
                        model.updateBodies();
                        physicsPanel.repaint();
                        if(controller.isFocused()){
                            view.setBodyFocus(model.getDestroyerBody());
                            view.updateInfoBox(view.getBodyFocus());
                            physicsPanel.setBodyFocus(view.getBodyFocus());
                            model.setRemovedBody(null);
                        }
                    }
                });
                view.setTimer(timer);
                timer.setRepeats(true);
                timer.start();
            }
        }); 
        JLabelSwingWorker workerThread = new JLabelSwingWorker();
        workerThread.setModel(model);
        workerThread.setView(view);
        workerThread.run();
    }
    private static class JLabelSwingWorker extends SwingWorker<Void, Integer>{
        private View view;
        private PhysicsHandler model;
        @Override
        protected Void doInBackground() throws Exception {
            int counter = 1;
            while(true){
                Thread.sleep(75);
                publish(counter);
            }
        }

        @Override
        protected void process(List<Integer> a) {
            view.updateInfoBoxSpeed(view.getBodyFocus());
        }
        public void setModel(PhysicsHandler model){
            this.model = model;
        }
        public void setView(View view){
            this.view = view;
        }
    }
}
