package com.mt8163tools.modules.voltage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.mt8163tools.R
import com.mt8163tools.databinding.FragmentVoltageBinding
import java.util.Locale

class VoltageFragment : Fragment() {
    private var _binding: FragmentVoltageBinding? = null
    private val binding: FragmentVoltageBinding
        get() = checkNotNull(_binding)

    private lateinit var settings: VoltageSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVoltageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings = VoltageSettings(requireContext())

        binding.voltageOffsetSeekBar.max =
            ((VoltageSettings.MAX_VOLTAGE_OFFSET - VoltageSettings.MIN_VOLTAGE_OFFSET) * 10).toInt()

        binding.voltageOffsetSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        renderOffset(progressToOffset(progress))
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) = Unit

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    settings.setVoltageOffset(progressToOffset(seekBar.progress))
                }
            }
        )

        binding.voltageResetButton.setOnClickListener {
            settings.setVoltageOffset(VoltageSettings.DEFAULT_VOLTAGE_OFFSET)
            binding.voltageOffsetSeekBar.progress = offsetToProgress(
                VoltageSettings.DEFAULT_VOLTAGE_OFFSET
            )
            renderOffset(VoltageSettings.DEFAULT_VOLTAGE_OFFSET)
        }

        val savedOffset = settings.getVoltageOffset()
        binding.voltageOffsetSeekBar.progress = offsetToProgress(savedOffset)
        renderOffset(savedOffset)
    }

    private fun progressToOffset(progress: Int): Float =
        VoltageSettings.MIN_VOLTAGE_OFFSET + progress / 10.0f

    private fun offsetToProgress(voltageOffset: Float): Int =
        ((voltageOffset - VoltageSettings.MIN_VOLTAGE_OFFSET) * 10).toInt()

    private fun renderOffset(voltageOffset: Float) {
        binding.voltageOffsetValue.text =
            String.format(Locale.ENGLISH, "%+.1f V", voltageOffset)
    }

    override fun onDestroyView() {
        _binding?.let {
            it.voltageOffsetSeekBar.setOnSeekBarChangeListener(null)
            it.voltageResetButton.setOnClickListener(null)
        }
        _binding = null
        super.onDestroyView()
    }
}
