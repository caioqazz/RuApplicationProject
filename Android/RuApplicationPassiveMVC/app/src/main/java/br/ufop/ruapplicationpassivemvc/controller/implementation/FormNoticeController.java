package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import br.ufop.ruapplicationpassivemvc.activity.listener.FormNoticeControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.NoticeServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.service.NoticeService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.util.Validate;
import br.ufop.ruapplicationpassivemvc.view.FormNoticeView;

import java.util.List;

import retrofit2.Response;

public class FormNoticeController implements NoticeServiceListener {

    private FormNoticeView formNoticeView;
    private FormNoticeControllerListener listener;
    private TokenManager tokenManager;
    private NoticeService noticeService;

    public FormNoticeController(FormNoticeView formNoticeView, FormNoticeControllerListener listener) {
        this.formNoticeView = formNoticeView;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(formNoticeView.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        setSpinner();
    }

    private void setSpinner() {
        String[] colors = {"Selecione o tipo de aviso", "Informativo", "Alerta", "Urgência"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(formNoticeView.getContext(), android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formNoticeView.setAdapterSpinner(spinnerArrayAdapter);// The drop down view
    }

    public void insert() {
        noticeService = new NoticeService(this, tokenManager);
        if (Validate.isFormComplete(formNoticeView.getSubject(), formNoticeView.getType(), formNoticeView.getContent())
                && Networking.isNetworkConnected(formNoticeView)) {

            formNoticeView.showLoading();
            noticeService.insert(formNoticeView.getSubject(), formNoticeView.getContent(), formNoticeView.getType());

        } else {
            Toast.makeText(formNoticeView.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(Notice notice) {
        noticeService = new NoticeService(this, tokenManager);
        if (Networking.isNetworkConnected(formNoticeView) && Validate.formWasUpdated(formNoticeView.getSubject(), formNoticeView.getType(), formNoticeView.getContent(), notice)) {
            formNoticeView.showLoading();
            noticeService.update(notice.getId(), formNoticeView.getSubject(), formNoticeView.getType(), formNoticeView.getContent());
        } else {
            Toast.makeText(formNoticeView.getContext(), "Modifique pelo menos um campo para atualizar.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onActionSuccess(Response<Notice> response, int option) {
        formNoticeView.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, formNoticeView.getSubject()), Constants.SUCCESS);

    }

    @Override
    public void onActionError(Response<Notice> response, int option) {
        formNoticeView.showForm();
        listener.onActionExecuted(ReplyAction.replyActionError(option, formNoticeView.getSubject()), Constants.ERROR);
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        formNoticeView.showForm();
        // listener.onActionExecuted(ReplyAction.replyActionError(option,formNoticeView.getSubject()), Constants.ERROR);

    }

    @Override
    public void onIndexSuccess(Response<List<Notice>> response) {

    }

    @Override
    public void onIndexError(Response<List<Notice>> response) {

    }


}
