package com.purang.myluckmeasuringapp.game_activity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityClimbLadderBinding
import kotlin.random.Random

class ClimbLadderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityClimbLadderBinding
    //private lateinit var drawView: DrawView
    private lateinit var ladderArr : Array<IntArray> //Array(1) { IntArray(1) { 1 } } // init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClimbLadderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        randomLadder()

        // LadderView 생성
        val ladderView = LadderView(this, null)

        // 레이아웃에 추가
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.ladderLl.addView(ladderView, layoutParams)
        /*drawView = DrawView(this)
        binding.ladderLl.addView(drawView)*/
        /*binding.ladderBtn.setOnClickListener {
            drawView.clearCanvas() // 캔버스 초기화
            DrawView().drawLadder() // 사다리 그리기
        }*/

        // "결과" 버튼 클릭 이벤트 처리
        /*btnResult.setOnClickListener {
            val result = (1..10).random() // 랜덤한 결과 생성
            Toast.makeText(this, "결과: $result", Toast.LENGTH_SHORT).show() // 결과를 토스트 메시지로 표시
        }*/
    }

    private fun randomLadder() {
        var rand = Random.nextInt(8) + 2
        rand = if (rand % 2 == 0) {
            rand + 1
        } else {
            rand
        }
        println("rand " + rand)
        val tempArr = Array(rand) {IntArray(rand) {0} }
        ladderArr = tempArr

        /*//출력값
        for ((i, row) in array2.withIndex()){
            for ((j,column) in row.withIndex()){
                print("[$i,$j] => $column\t")
                //출력
                //[0.0] => 0, [0,1] =>0 ...
            }
            println()
        }*/
        //i는 순서, row는 행의 값
        //짝수 줄만 1로 변경(시작지점 세팅 및 이동가능 설정)
        for ((i, row) in tempArr.withIndex()) {
            println()
            for ((j,column) in row.withIndex()){
                if (j % 2 == 0) {
                    //ladderArr[0][j] = 1
                    //ladderArr[rand-1][j] = 1
                    ladderArr[i][j] = 1
                }
                //print("[$i,$j] => $column\t")
            }
        }

        //홀수줄에 랜덤 추가
        // 홀수 줄에 랜덤 추가
        for ((i, row) in tempArr.withIndex()) {
            for ((j, column) in row.withIndex()) {
                val tempRand = Random.nextInt(rand - 1) + 1 // 2부터 rand-3 사이의 난수 생성, column의 1을 넣을 idx
                if (j % 2 != 0) {
                    // 현재 열의 이전과 다음 열에 1이 없는 경우에만 값을 추가
                    val prevValue = if (j > 2) ladderArr[i][j - 2] else -1 // 이전 열의 값 (없으면 -1)
                    val nextValue = if (j < rand - 3) ladderArr[i][j + 2] else -1 // 다음 열의 값 (없으면 -1)

                    // 현재 행에 이미 값이 있는 경우에도 값을 추가해야 함
                    val currentValue = ladderArr[i][tempRand]

                    // 현재 행의 이전 행과 다음 행에 이미 값이 없는 경우에만 값을 추가
                    val prevRowValue = if (i > 0) ladderArr[i - 1][tempRand] else -1 // 이전 행의 값 (없으면 -1)
                    val nextRowValue = if (i < rand - 1) ladderArr[i + 1][tempRand] else -1 // 다음 행의 값 (없으면 -1)

                    // 양 옆과 위 아래에 1이 없는 경우 또는 현재 행에 이미 값이 있는 경우에만 값을 추가
                    if ((prevValue != 1 && nextValue != 1 && currentValue != 1)
                        &&
                        (prevRowValue != 1 && nextRowValue != 1)
                    ) {
                        ladderArr[i][tempRand] = 1
                    }
                    //ladderArr[i][j] = 0
                } else {
                    //ladderArr[i][j] = 1
                }
            }
        }

        for (j in 0 until ladderArr.size - 1) {
            if (j % 2 == 0) {
                ladderArr[0][j] = 1
                ladderArr[ladderArr.size - 1][j] = 1
            } else {
                ladderArr[0][j] = 0
                ladderArr[ladderArr.size - 1][j] = 0
            }
        }
    }
}

class LadderView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()

    private val matrix = arrayOf(
        intArrayOf(1, 0, 1, 0, 1),
        intArrayOf(1, 1, 1, 0, 1),
        intArrayOf(1, 0, 1, 0, 1)
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 셀의 너비와 높이 계산
        val cellWidth = width / (matrix[0].size+2).toFloat()
        val cellHeight = height / (matrix.size*2).toFloat()
        Log.e("cellWidth", cellWidth.toString())
        Log.e("cellheight", cellHeight.toString())
        paint.strokeWidth = 6f //두께

        // 행을 반복하여 사다리 그리기
        for (i in matrix.indices) {
            // 열을 반복하여 각 셀 그리기
            for (j in matrix[i].indices) {
                // 현재 셀의 좌표 계산
                // 현재 셀의 좌표 계산
                val startX = j * cellWidth + cellWidth / 2
                val startY = i * 2 * cellHeight
                val endX = (j + 1) * cellWidth - cellWidth / 2
                val endY = (i * 2 + 1) * cellHeight

                // 현재 셀의 값이 1이면 사다리를 그림
                if (matrix[i][j] == 1) {
                    // 짝수 행은 수평 막대를 그림
                    if (i % 2 == 0) {
                        canvas.drawLine(startX, startY, endX, startY, paint)
                    } else {
                        // 홀수 행은 수직 막대를 그림
                        canvas.drawLine(startX, startY, startX, endY, paint)
                    }
                }
                /*if (matrix[i][j] == 1) {
                    // 짝수 행은 수평 막대를 그림
                    if (i % 2 == 0) {
                        canvas.drawLine("100".toFloat(), "100".toFloat(), "100".toFloat(), "100".toFloat(), paint)
                    } *//*else {
                        // 홀수 행은 수직 막대를 그림
                        canvas.drawLine(startX, startY, startX, endY, paint)
                    }*//*
                }*/
            }
        }
    }
}
/*
class DrawView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint()

    private var ladderWidth = 10 // 사다리 가로줄 수
    private var ladderHeight = 5 // 사다리 세로줄 수

    private var rungsGap = 100 // 사다리 가로 간격
    private var rungsHeight = 50 // 사다리 세로 간격

    private var startX = 100f // 사다리 시작 X 좌표
    private var startY = 100f // 사다리 시작 Y 좌표

    private val rungColors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLadder(canvas)
    }

    private fun drawLadder(canvas: Canvas) {
        paint.color = Color.BLACK
        var currentX = startX
        var currentY = startY

        // 사다리 가로줄 그리기
        for (i in 0 until ladderWidth) {
            val nextX = currentX + rungsGap
            val nextY = currentY

            // 선 그리기
            canvas.drawLine(currentX, currentY, nextX, nextY, paint)

            // 다음 가로줄로 이동
            currentX = nextX
            currentY = nextY

            // 사다리 세로줄 그리기
            drawRungs(canvas, currentX, currentY)

            // 가로 간격 조정
            currentX += rungsGap
        }
    }

    private fun drawRungs(canvas: Canvas, startX: Float, startY: Float) {
        var currentX = startX
        var currentY = startY

        paint.strokeWidth = 3f
        // 사다리 세로줄 그리기
        for (i in 0 until ladderHeight) {
            val nextX = currentX
            val nextY = currentY + rungsHeight

            // 선 그리기
            paint.color = rungColors[i % rungColors.size] // 사다리 색상 순환
            canvas.drawLine(currentX, currentY, nextX, nextY, paint)

            // 다음 세로줄로 이동
            currentX = nextX
            currentY = nextY
        }
    }

    // 캔버스 초기화 메서드
    fun clearCanvas() {
        invalidate() // 화면 갱신
    }
}*/
