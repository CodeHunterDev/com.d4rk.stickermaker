package com.d4rk.stickermaker.activities
import android.app.Dialog
import android.os.Bundle
import com.d4rk.stickermaker.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
open class RoundedBottomSheet : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}