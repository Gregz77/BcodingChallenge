package com.greggz77.bcodingchallenge.presenter

import com.greggz77.bcodingchallenge.contract.ContractInterface
import com.greggz77.bcodingchallenge.contract.ContractInterface.Model
import com.greggz77.bcodingchallenge.contract.ContractInterface.Presenter
import com.greggz77.bcodingchallenge.model.MainActivityModel

class MainActivityPresenter(_view: ContractInterface.View): Presenter {

    private var view: ContractInterface.View = _view
    private var model: Model = MainActivityModel()
}