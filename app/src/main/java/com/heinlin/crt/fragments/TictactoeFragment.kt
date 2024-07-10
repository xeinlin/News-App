package com.heinlin.crt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heinlin.crt.base.BaseFragment
import com.heinlin.crt.databinding.FragmentTictactoeBinding
import mkkcom.example.cagaco.GameData
import mkkcom.example.cagaco.GameModel
import mkkcom.example.cagaco.GameStatus

class TictactoeFragment : BaseFragment<FragmentTictactoeBinding>(), View.OnClickListener {
    private var gameModel: GameModel? = null

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTictactoeBinding {
        return FragmentTictactoeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)

        binding.startGameBtn.setOnClickListener {
            startGame()

        }
        GameData.gameModel.observe(viewLifecycleOwner) {
            gameModel = it
            setUI()
        }


    }

    fun setUI() {
        gameModel?.apply {
            binding.btn0.text = filledPos[0]
            binding.btn1.text = filledPos[1]
            binding.btn2.text = filledPos[2]
            binding.btn3.text = filledPos[3]
            binding.btn4.text = filledPos[4]
            binding.btn5.text = filledPos[5]
            binding.btn6.text = filledPos[6]
            binding.btn7.text = filledPos[7]
            binding.btn8.text = filledPos[8]

            binding.startGameBtn.visibility = View.VISIBLE

            binding.gameStatusText.text = when (gameStatus) {
                GameStatus.CREATED -> {
                    binding.startGameBtn.visibility = View.INVISIBLE
                    "Game ID :$gameId"
                }

                GameStatus.JOINED -> {
                    "Click on start game"

                }

                GameStatus.INPROGRESS -> {
                    binding.startGameBtn.visibility = View.INVISIBLE
                    "$currentPlayer turn"
                }

                GameStatus.FINISHED -> {
                    if (winner.isNotEmpty()) "$winner Won"
                    else "DRAW"
                }
            }

        }

    }

    fun startGame() {
        gameModel?.apply {
            updateGameData(
                GameModel(
                    gameId = gameId, gameStatus = GameStatus.INPROGRESS
                )
            )

        }


    }

    fun updateGameData(model: GameModel) {
        GameData.saveGameModel(model)
    }


    override fun onClick(v: View?) {
        gameModel?.apply {
            if (gameStatus != GameStatus.INPROGRESS) {
                return
            }
            //game is in progress
            val clickedPos = (v?.tag as String).toInt()
            if (filledPos[clickedPos].isEmpty()) {
                filledPos[clickedPos] = currentPlayer
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateGameData(this)
            }

        }
    }


}